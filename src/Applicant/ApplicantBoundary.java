package Applicant;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import Enquiry.Enquiry;
import Enumerations.MaritalStatus;
import Manager.ManagerController;
import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;
import Reply.Reply;
import Reply.ReplyController;
import Utils.SafeScanner;
import User.UserBoundary;
import Enquiry.EnquiryController;


public class ApplicantBoundary {
    private Applicant applicant;

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
            System.out.println("6. ");
            System.out.println("7. Change Password");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 7);

            switch (choice) {
                case 1 -> viewApplicantProfile();
                case 2 -> {
                    ProjectController.showFilterMenu(applicant.getID(), sc);
                    displayProjectsForApplicant(applicant);
                }
                case 3 -> applyForProject(applicant);
                case 4 -> viewApplication(applicant);
                case 5 -> applicantEnquiryMenu(applicant);
                case 6 -> System.out.println("blanshs");
                case 7 -> changePassword();
                case 0 -> System.out.println("Exiting the Applicant Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0 && choice !=7) ;
        if (choice == 0){
            sc.close();
        }
    }


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

    private void changePassword() {
        UserBoundary.changePassword(applicant.getUserProfile());
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

    public static void applyForProject(Applicant applicant) {
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
            ValidprojIDs.forEach(item -> System.out.println("ID: " + item));
            // TO BE UPDATED
            // BEST TO GET FILTERED VALID PROJECT ID BASED ON APPLICANT'S ATTRIBUTE
            String projectID = SafeScanner.getValidatedStringInput(sc,"Enter Project ID you want to apply for: ",ValidprojIDs);
            String roomType;
            if(applicant.getMaritalStatus() == MaritalStatus.SINGLE){
                roomType = "2-room";
            }
            else {
                List<String> validRoomTypes = Arrays.asList("2-room", "3-room");
                roomType = SafeScanner.getValidatedStringInput(sc, "Enter a room type (2-Room or 3-Room): ", validRoomTypes);
            }

            // checks that at least 1 unit available for each type
            Project availUnits = ProjectController.getProjectByID(projectID);
            if (roomType.equalsIgnoreCase("2-room") && availUnits.getNoOfUnitsType1() < 1) {
                System.out.println("No 2-room units available for this project.");
                return;
            } else if (roomType.equalsIgnoreCase("3-room") && availUnits.getNoOfUnitsType2() < 1) {
                System.out.println("No 3-room units available for this project.");
                return;
            }

            if (!ProjectApplicationController.createProjectApplication(projectID.toUpperCase(),roomType, applicant.getID())){
                System.out.println("Project application could not be created.");
            }
            else{
                System.out.println("Application created successfully!");
            }

        }
    }

    public static void viewApplication(Applicant applicant) {
        List<ProjectApplication> application = ProjectApplicationController.getApplicationByApplicantID(applicant.getID());
        if (application.isEmpty()){
            System.out.println("Application could not be found.");
        }
        else {
            application.forEach(
                    ApplicantBoundary::prettyPrintProjectApplications
            );
        }
    }

    public static void displayProjectsForApplicant(Applicant applicant) {
        // 1) now returns a list
        List<ProjectApplication> applications =
                ProjectApplicationController.getApplicationByApplicantID(applicant.getID());

        // 2) collect all the project‑IDs this applicant has already applied to
        Set<String> appliedIds = new HashSet<>();
        if (!applications.isEmpty()) {
            System.out.println("\nYou have applied to the following project"
                    + (applications.size() > 1 ? "s:" : ":"));
            for (ProjectApplication app : applications) {
                appliedIds.add(app.getProjectID());
                Project p = ProjectController.getProjectByID(app.getProjectID());
                prettyPrintApplicantProject(applicant, p);
            }
        }

        // 3) fetch the whole “open” list, then skip anything in appliedIds
        List<Project> filteredProjects = ProjectController.getProjectsForApplicant(applicant);
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects are open to your user group.");
        } else {
            System.out.println("========= Projects =========");
            for (Project proj : filteredProjects) {
                prettyPrintApplicantProject(applicant, proj);
            }
        }
    }
    public static void prettyPrintApplicantProject(Applicant applicant, Project project) {
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd MMM yyyy");
        if (applicant.getMaritalStatus() == MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            System.out.println("\nProject ID: "+project.getID());
            System.out.println("Project name: " + project.getProjectName());
            System.out.println("Neighbourhood: " + project.getNeighbourhood());
            if(project.getType1().equalsIgnoreCase("2-Room")){
                System.out.println("Room Type 1: "+project.getType1());
                System.out.println("Number of units for Room Type 1: "+project.getNoOfUnitsType1());
                System.out.println("Selling price of Room Type 1: "+project.getSellPriceType1());
            }
            else {
                System.out.println("Room Type 2: "+project.getType2());
                System.out.println("Number of units for Room Type 2: "+project.getNoOfUnitsType2());
                System.out.println("Selling price of Room Type 2: "+project.getSellPriceType2());
            }
            System.out.println("Application Open Date: "+project.getAppDateOpen().format(fmt1));
            System.out.println("Application Close Date: "+project.getAppDateClose().format(fmt1));
            System.out.println("Manager-in-charge: "+ ManagerController.getNameById(project.getManagerID()));

            System.out.println("------------------------");
        }
        else{
            System.out.println("\nProject ID: "+project.getID());
            System.out.println("Project name: " + project.getProjectName());
            System.out.println("Neighbourhood: " + project.getNeighbourhood());
            System.out.println("Room Type 1: "+project.getType1());
            System.out.println("Number of units for Room Type 1: "+project.getNoOfUnitsType1());
            System.out.println("Selling price of Room Type 1: "+project.getSellPriceType1());
            System.out.println("Room Type 2: "+project.getType2());
            System.out.println("Number of units for Room Type 2: "+project.getNoOfUnitsType2());
            System.out.println("Selling price of Room Type 2: "+project.getSellPriceType2());
            System.out.println("Application Open Date: "+project.getAppDateOpen().format(fmt1));
            System.out.println("Application Close Date: "+project.getAppDateClose().format(fmt1));
            System.out.println("Manager-in-charge: "+ManagerController.getNameById(project.getManagerID()));

            System.out.println("------------------------");
        }
    }
    public static void prettyPrintProjectApplications(ProjectApplication application) {
        if (application == null) {
            System.out.println("No Application available.");
            return;
        }
        Project project = ProjectController.getProjectByID(application.getProjectID());
        System.out.println("\n======== Project Application ========\n");
        if (project == null) {
            System.out.println("Project details are not available.");
        }
        System.out.println("Application ID: " + application.getID());
        assert project != null;
        System.out.println("Project Name: " + project.getProjectName());
        System.out.println("Project ID: " + application.getProjectID());
        System.out.println("Room Type Applied: "+application.getRoomType());
        System.out.println("Status: " + application.getStatus());
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
                .filter(e -> {
                    List<Reply> replies = ReplyController.getRepliesByEnquiry(e.getEnquiryId());
                    return replies != null && !replies.isEmpty();
                })
                .collect(Collectors.toList());

        if (repliedEnquiries.isEmpty()) {
            System.out.println("You have no replied enquiries.");
            return;
        }

        // Display replied enquiry IDs and their messages
        System.out.println("Replied Enquiries:");
        for (Enquiry e : repliedEnquiries) {
            System.out.println("Enquiry ID: " + e.getEnquiryId() + " | Message: " + e.getMessage());
        }

        // Get valid enquiry IDs in lowercase for case-insensitive match
        List<String> validIds = repliedEnquiries.stream()
                .map(e -> e.getEnquiryId().toLowerCase())
                .toList();

        // Get validated input
        String enquiryIdInput = SafeScanner.getValidatedStringInput(sc,
                "\nEnter Enquiry ID to view replies: ", validIds);

        // Match and normalize to actual case
        String finalEnquiryId = repliedEnquiries.stream()
                .map(Enquiry::getEnquiryId)
                .filter(enquiryId -> enquiryId.equalsIgnoreCase(enquiryIdInput))
                .findFirst()
                .orElse(enquiryIdInput); // fallback (shouldn't happen)

        // Print replies
        List<Reply> replies = ReplyController.getRepliesByEnquiry(finalEnquiryId);
        for (Reply reply : replies) {
            printPrettyReply(reply);
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
    public static void printPrettyReply(Reply reply) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Reply ID: " + reply.getReplyId());
        System.out.println("Enquiry ID: " + reply.getEnquiryId());
        System.out.println("Date: " + reply.getDate().format(dateFormatter));
        System.out.println("Officer / Manager ID: "+reply.getOfficerOrManagerId());
        System.out.println("Reply: "+reply.getReplyContent());
        System.out.println("----------------------\n");
    }

}
