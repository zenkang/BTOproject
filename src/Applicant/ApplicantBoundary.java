package Applicant;


import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Abstract.IUserProfile;
import Enquiry.Enquiry;
import Enumerations.EnquiryStatus;
import Enumerations.MaritalStatus;
import Manager.ManagerController;
import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;
import Reply.Reply;
import Reply.ReplyController;
import Utils.PredicateUtils;
import Utils.SafeScanner;
import User.UserBoundary;
import Enquiry.EnquiryController;


public class ApplicantBoundary {
    private Applicant applicant;
    private static Predicate<Project> Filter = null;
    private static Predicate<Project> neighbourhoodFilter = null;
    private static Predicate<Project> flatTypeFilter = null;
    private static String Filterneighbourhood = null;
    private static String FilterflatType = null;

    public ApplicantBoundary(Applicant applicant) {
        this.applicant = applicant;
    }

    public void displayMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n=== Applicant Menu ===");
            System.out.println("1. View/update my profile");
            System.out.println("2. View Projects");
            System.out.println("3. Apply Projects");
            System.out.println("4. View my Application");
            System.out.println("5. View Enquiry Menu");
            System.out.println("6. Change Password");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 6);

            switch (choice) {
                case 1 -> viewApplicantProfile();
                case 2 -> displayProjectMenu(applicant);
                case 3 -> applyForProject(applicant);
                case 4 -> viewApplication(applicant);
                case 5 -> applicantEnquiryMenu(applicant);
                case 6 -> UserBoundary.changePassword(applicant.getUserProfile());
                case 0 -> System.out.println("Exiting the Applicant Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0 && choice !=6) ;
        if (choice == 0){
            sc.close();
        }
    }

//User profile functions
    public void viewApplicantProfile() {
        Scanner sc = new Scanner(System.in);
        String selection;
        do {
            Utils.PrettyPrint.prettyPrint(applicant);

            List<String> validOptions = Arrays.asList("y", "n");
            selection = SafeScanner.getValidatedStringInput(sc,"Would you like to update your profile?\nEnter: y/n\n",validOptions);
            if (selection.equals("y")) {
                updateApplicantProfile();
            }
        }while(!selection.equals("n"));

    }

    public void updateApplicantProfile() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do{
            Utils.PrettyPrint.prettyUpdate(applicant);

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 3);

            switch (choice) {
                case 1 -> updateName();
                case 2 -> updateAge();
                case 3 -> updateMaritalStatus();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);

    }


    private void updateAge(){
        Scanner sc = new Scanner(System.in);
        int age;
        age = SafeScanner.getValidatedIntInput(sc, "Enter your new age: ", 0, 200);
        if(ApplicantController.updateAge(applicant, age)){
            System.out.println("Your age has been updated!\n");
        }
        else{
            System.out.println("Update failed, try again later\n");
        }
    }

    private void updateName(){
        Scanner sc = new Scanner(System.in);
        String newName = SafeScanner.getValidatedStringInput(sc,"Enter your new Name: ",50);
        if(ApplicantController.updateName(applicant, newName)){
            System.out.println("Your name has been updated!\n");
        }
        else{
            System.out.println("Update failed, try again later\n");
        }
    }

    private void updateMaritalStatus(){
        Scanner sc = new Scanner(System.in);
        List<String> validOptions = Arrays.asList("m", "s");
        String maritalStatus = SafeScanner.getValidatedStringInput(sc,"Enter your marital status: ( m: Married , s: Single)\n", validOptions);
        if(ApplicantController.updateMaritalStatus(applicant, maritalStatus)){
            System.out.println("Your marital status has been updated!\n");
        }
        else{
            System.out.println("Update failed, try again later\n");
        }
    }


//Applications functions

    public static <T extends IUserProfile> void applyForProject(T applicant) {
        Scanner sc = new Scanner(System.in);
        if (!ProjectApplicationController.checkPreviousApplication(applicant.getNric())) {
            System.out.println("You have already submitted an Application Before.");
            return;
        }
        if(applicant.getAge() < 21){
            System.out.println("You must be older than 21 to apply.");
            return;
        }
        if(applicant.getMaritalStatus() == MaritalStatus.SINGLE && applicant.getAge()<35){
            System.out.println("Single applicants have to be 35 years old and above to apply.");
            return;
        }
        if(ProjectController.getProjectsForApplicant(applicant).isEmpty()) {
            System.out.println("No Projects available for applications at the moment.");
        }

        else {
            System.out.println("\n=== Project Application ===");
            System.out.println("Valid Projects:");
            List<String> ValidprojIDs = ProjectController.getProjectIDsForApplicant(applicant);
            System.out.println("ID: "+ValidprojIDs);
            String projectID = SafeScanner.getValidatedStringInput(sc,"Enter Project ID you want to apply for: ",ValidprojIDs);
            Project p = ProjectController.getProjectByID(projectID);
            String roomType;
            if(applicant.getMaritalStatus() == MaritalStatus.SINGLE){
                roomType = "2-room";
                p.prettyPrint4SingleApplicant();
            }
            else {
                p.prettyPrint4MarriedApplicant();
                List<String> validRoomTypes = Arrays.asList("2-room", "3-room");
                roomType = SafeScanner.getValidatedStringInput(sc, "\nEnter a room type (2-Room or 3-Room): ", validRoomTypes);
            }
            // checks that at least 1 unit available for each type
            if (roomType.equalsIgnoreCase("2-room")){
                if(( p.getType1().equalsIgnoreCase("2-room") && p.getNoOfUnitsType1() < 1) ||( p.getType2().equalsIgnoreCase("2-room") && p.getNoOfUnitsType2() < 1) ){
                    System.out.println("No 2-room units available for this project.");
                    return;
                }
            } else if (roomType.equalsIgnoreCase("3-room")) {
                if(( p.getType1().equalsIgnoreCase("3-room") && p.getNoOfUnitsType1() < 1) ||( p.getType2().equalsIgnoreCase("3-room") && p.getNoOfUnitsType2() < 1) ){
                     System.out.println("No 3-room units available for this project.");
                     return;
                }
            }
            if (!ProjectApplicationController.createProjectApplication(projectID.toUpperCase(),roomType, applicant.getNric())){
                System.out.println("Project application could not be created.");
            }
            else{
                System.out.println("Application created successfully!");
            }

        }
    }

    public static <T extends IUserProfile> void viewApplication(T applicant) {
        List<ProjectApplication> failApp = ProjectApplicationController.getUnsuccessApplicationByApplicantID(applicant.getNric());
        ProjectApplication currentApp = ProjectApplicationController.getCurrentApplicationByApplicantID(applicant.getNric());
        if (failApp.isEmpty() && currentApp == null) {
            System.out.println("Application could not be found.");
            return;
        }
        if(!failApp.isEmpty()){
            System.out.println("\n________Unsuccessful Applications________");
            for(ProjectApplication app : failApp){
                app.prettyPrintApplicant();
            }
        }
        if(currentApp != null){
            System.out.println("\n__________Current Application__________");
            currentApp.prettyPrintApplicant();
            Project p = ProjectController.getProjectByID(currentApp.getProjectID());
            System.out.println("\n________Project_________");
            if(currentApp.getRoomType().equalsIgnoreCase("2-room")){
                p.prettyPrint4SingleApplicant();
            }
            else{
                p.prettyPrintApplicant3room();
            }
            Scanner sc = new Scanner(System.in);
            System.out.println("\nEnter W to withdraw application,\nEnter anything else to go back: ");
            String selection = sc.nextLine().trim();
            if(selection.equalsIgnoreCase("W")){
                withdrawApplication(applicant);
            }
        }
    }

//Project functions
    public static <T extends IUserProfile> void displayProjectMenu(T applicant) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== Project Menu =====");
            System.out.println("1. View available projects");
            System.out.println("2. View project you have applied to");
            System.out.println("3. Update filters");
            System.out.println("0. Exit");
            choice  = SafeScanner.getValidatedIntInput(sc, "Enter option: ", 0, 3);

            switch (choice) {
                case 1 -> displayAvailProjectsForApplicant(applicant);
                case 2 -> displayAppliedProjects(applicant);
                case 3 -> displayProjectFilterMenu();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option.");
            }
        }while(choice !=0);
    }

    public static <T extends IUserProfile> void displayAppliedProjects(T applicant) {
        List<ProjectApplication> applications =
                ProjectApplicationController.getApplicationsByApplicantID(applicant.getNric());
        if (!applications.isEmpty()) {
            System.out.println("\nYou have applied to the following project"
                    + (applications.size() > 1 ? "s:" : ":"));
            for (ProjectApplication app : applications) {
                Project p = ProjectController.getProjectByID(app.getProjectID());
                if (applicant.getMaritalStatus() == MaritalStatus.SINGLE) {
                    p.prettyPrint4SingleApplicant();
                } else {
                    p.prettyPrint4MarriedApplicant();
                }
                System.out.println("Manager-in-charge: " + ManagerController.getNameById(p.getManagerID()));
                System.out.println("Status: " + app.getStatus());
            }
        }
    }
    public static <T extends IUserProfile> void displayAvailProjectsForApplicant(T applicant) {
        List<Project> filteredProjects = ProjectController.getFilteredProjectsForApplicant(applicant,Filter);
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects are open to your user group.");
        } else {
            System.out.println("\n========= Projects Available =========");
            for (Project proj : filteredProjects) {
                if((applicant.getMaritalStatus() == MaritalStatus.SINGLE)){
                    proj.prettyPrint4SingleApplicant();
                } else if (FilterflatType == null) {
                    proj.prettyPrint4MarriedApplicant();
                } else if (FilterflatType.equalsIgnoreCase("3-room")) {
                    proj.prettyPrintApplicant3room();
                } else{
                    proj.prettyPrint4SingleApplicant();
                }
                System.out.println("Manager-in-charge: "+ ManagerController.getNameById(proj.getManagerID()));
            }
            Scanner sc = new Scanner(System.in);
            System.out.println("\nEnter A to apply,\n      E to submit enquiry\nEnter anything else to go back: ");
            String selection = sc.nextLine().trim();
            if(selection.equalsIgnoreCase("E")){
                submitEnquiry(applicant.getNric());
            }
            else if(selection.equalsIgnoreCase("A")){
                applyForProject(applicant);
            }
        }
    }

    public static void displayProjectFilterMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Project Filter Menu ===");
            System.out.println("1. Neighbourhood: "+Filterneighbourhood);
            System.out.println("2. Flat Type: "+FilterflatType);
            System.out.println("3. Reset Filters");
            System.out.println("0. Exit");
            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 3);

            switch (choice) {
                case 1 -> {
                    Filterneighbourhood = SafeScanner.getValidatedStringInput(sc,"Enter the neighbourhood: ",100);
                    neighbourhoodFilter = project -> project.getNeighbourhood().equalsIgnoreCase(Filterneighbourhood);
                }
                case 2 -> {
                    List<String> validRoomOptions = Arrays.asList("2-room", "3-room");
                    FilterflatType = SafeScanner.getValidatedStringInput(sc, "Enter flat type filter(e.g.,2-Room,3-Room:)", validRoomOptions);
                    flatTypeFilter = project -> ((project.getType1().equalsIgnoreCase(FilterflatType) && project.getNoOfUnitsType1()>0)||
                            (project.getType2().equalsIgnoreCase(FilterflatType) && project.getNoOfUnitsType2()>0));
                }
                case 3 -> {
                    Filter = null;
                    FilterflatType = null;
                    Filterneighbourhood = null;
                    neighbourhoodFilter = null;
                    flatTypeFilter = null;
                    System.out.println("Filter Reset.");
                }
                case 0 -> {
                    System.out.println("Filter preferences updated.");
                    System.out.println("Exiting the Project Filter Menu.");
                }
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0 && choice != 3);
        Filter = PredicateUtils.combineFilters(neighbourhoodFilter, flatTypeFilter);
    }

    public static void applicantEnquiryMenu(Applicant applicant) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Enquiry Menu (Applicant) ---");
            System.out.println("1. Submit new enquiry");
            System.out.println("2. View my enquiries");
            System.out.println("3. Edit an existing enquiry");
            System.out.println("4. Delete an enquiry");
            System.out.println("5. View replies");
            System.out.println("0. Exit");
            choice  = SafeScanner.getValidatedIntInput(sc, "Enter option: ", 0, 5);

            switch (choice) {
                case 1 -> submitEnquiry(applicant.getNric());
                case 2 -> viewEnquiries(applicant.getNric());
                case 3 -> editEnquiry(applicant.getNric());
                case 4 -> deleteEnquiry(applicant.getNric());
                case 5 -> viewRepliedEnquiry(applicant, sc);
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option.");
            }
        }while(choice !=0);
    }

    public static void submitEnquiry(String nric) {
        Scanner sc = new Scanner(System.in);
        String projectName = SafeScanner.getValidProjectName(sc);
        System.out.print("Enter your message: ");
        String message = sc.nextLine();
        LocalDate today = LocalDate.now();
        EnquiryController.addEnquiry(today,projectName,nric,message);

        System.out.println("Enquiry submitted!");
    }
    private static void viewRepliedEnquiry(Applicant applicant, Scanner sc) {
        System.out.println("\n--- View Replied Enquiry ---");

        // Get only enquiries by the applicant
        List<Enquiry> enquiries = EnquiryController.getEnquiriesByApplicant(applicant.getNric());

        // Filter those that have replies
        List<Enquiry> repliedEnquiries = enquiries.stream()
                .filter(e ->
                    e.getStatus().equals(EnquiryStatus.REPLIED))
                .toList();

        if (repliedEnquiries.isEmpty()) {
            System.out.println("You have no replied enquiries.");
            return;
        }

        // Display replied enquiry IDs and their messages
        System.out.println("Replied Enquiries:");
        for (Enquiry e : repliedEnquiries) {
            System.out.println("Enquiry ID: " + e.getEnquiryId() + " | Message: " + e.getMessage());
        }

        List<String> validIds = repliedEnquiries.stream()
                .map(e -> e.getEnquiryId().toLowerCase())
                .toList();

        String enquiryIdInput = SafeScanner.getValidatedStringInput(sc,
                "\nEnter Enquiry ID to view replies: ", validIds);

        // Print replies
        List<Reply> replies = ReplyController.getRepliesByEnquiry(enquiryIdInput);
        for (Reply reply : replies) {
            reply.printPrettyReply();
        }
    }


    public static void viewEnquiries(String nric) {
        List<Enquiry> enquiries = EnquiryController.getEnquiriesByApplicant(nric);
        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries.");
        } else {
            for (Enquiry e : enquiries) {
                System.out.println(e);
            }
        }
    }

    public static void editEnquiry(String nric) {
        Scanner sc = new Scanner(System.in);
        List<Enquiry> enquiries = EnquiryController.getEnquiriesByApplicant(nric);
        List<Enquiry> editable = enquiries.stream()
                .filter(e -> !e.isReplied())
                .toList();

        if (editable.isEmpty()) {
            System.out.println("No editable enquiries found. You cannot edit replied enquiries.");
            return;
        }

        System.out.println("Select an enquiry to edit:");
        for (int i = 0; i < editable.size(); i++) {
            System.out.println((i + 1) + ". " + editable.get(i));
        }

        int choice = SafeScanner.getValidatedIntInput(sc, "Choose: ", 1, editable.size());

        Enquiry enquiry = editable.get(choice - 1);
        System.out.print("Enter new message for enquiry: ");
        String newMessage = sc.nextLine();
        enquiry.setMessage(newMessage);

        EnquiryController.updateEnquiry(enquiry);
        System.out.println("Enquiry updated successfully!");
    }

    public static void deleteEnquiry(String nric) {
        Scanner sc = new Scanner(System.in);
        List<Enquiry> enquiries = EnquiryController.getEnquiriesByApplicant(nric);
        List<Enquiry> deletable = enquiries.stream()
                .filter(e -> !e.isReplied())
                .collect(Collectors.toList());

        if (deletable.isEmpty()) {
            System.out.println("No deletable enquiries found. You cannot delete replied enquiries.");
            return;
        }

        System.out.println("Select an enquiry to delete:");
        for (int i = 0; i < deletable.size(); i++) {
            System.out.println((i + 1) + ". " + deletable.get(i));
        }

        int choice = SafeScanner.getValidatedIntInput(sc, "Choose: ", 1, deletable.size());

        Enquiry enquiry = deletable.get(choice - 1);
        EnquiryController.deleteEnquiry(enquiry.getEnquiryId());

        System.out.println("Enquiry deleted successfully!");
    }

    private static <T extends IUserProfile> void withdrawApplication(T applicant) {
        ProjectApplication application = ProjectApplicationController.getCurrentApplicationByApplicantID(applicant.getNric());

        if (application == null) {
            System.out.println("No application to withdraw.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        String confirm = SafeScanner.getValidatedStringInput(
                sc, "Are you sure you want to withdraw your application? (y/n): ",
                List.of("y", "n")
        );

        if (confirm.equalsIgnoreCase("y")) {
            boolean success = ProjectApplicationController.withdrawApplication(applicant.getNric());
            if (success) {
                System.out.println("Application withdrawn successfully.");
            } else {
                System.out.println("Failed to withdraw the application.");
            }
        }
    }


}
