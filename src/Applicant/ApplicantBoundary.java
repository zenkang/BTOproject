package Applicant;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import Abstract.IUserProfile;
import Enquiry.Enquiry;
import Enquiry.EnquiryController;
import Enumerations.EnquiryStatus;
import Enumerations.MaritalStatus;
import Manager.ManagerController;
import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;
import Reply.Reply;
import Reply.ReplyController;
import Utils.ProjectFilterContext;
import Utils.SafeScanner;
import User.UserBoundary;

public class ApplicantBoundary {
    private final IUserProfile user;
    private final ProjectFilterContext filterContext;

    public ApplicantBoundary(IUserProfile user, ProjectFilterContext filterContext) {
        this.user = user;
        this.filterContext = filterContext;
    }

    public ApplicantBoundary(Applicant applicant) {
        this.user = applicant;
        this.filterContext = new ProjectFilterContext();
    }

    public void displayMenu() {
        if (!(user instanceof Applicant applicant)) {
            System.out.println("This menu is only available for applicants.");
            return;
        }

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
                case 1 -> viewApplicantProfile(applicant);
                case 2 -> displayProjectMenu();
                case 3 -> applyForProject();
                case 4 -> viewApplication();
                case 5 -> applicantEnquiryMenu(applicant);
                case 6 -> UserBoundary.changePassword(applicant.getUserProfile());
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    public void displayProjectMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n===== APPLICANT Project Menu =====");
            System.out.println("1. View available projects");
            System.out.println("2. View project you have applied to");
            System.out.println("3. Update filters");
            System.out.println("0. Exit");
            choice  = SafeScanner.getValidatedIntInput(sc, "Enter option: ", 0, 3);

            switch (choice) {
                case 1 -> displayAvailProjectsForApplicant();
                case 2 -> displayAppliedProjects();
                case 3 -> displayProjectFilterMenu();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

    private void displayAvailProjectsForApplicant() {
        List<Project> filteredProjects = new ArrayList<>(
                ProjectController.getFilteredProjectsForApplicant(user, filterContext.combinedFilter)
        );

        if (filteredProjects.isEmpty()) {
            System.out.println("No projects are open to your user group.");
            return;
        }

        System.out.println("\nFilters applied: " + filterContext.neighbourhood + " | " + filterContext.flatType);
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter 'o' to sort by Opening Date,\n      'c' to sort by Closing Date\nEnter anything else to continue: ");
        String selection = sc.nextLine().trim().toLowerCase();

        switch (selection) {
            case "c" -> filteredProjects.sort(Comparator.comparing(Project::getAppDateClose));
            case "o" -> filteredProjects.sort(Comparator.comparing(Project::getAppDateOpen));
        }

        System.out.println("\n========= Projects Available =========");
        for (Project proj : filteredProjects) {
            printProjectForApplicant(user, proj);
            System.out.println("Manager-in-charge: " + ManagerController.getNameById(proj.getManagerID()));
        }

        System.out.println("\nEnter 'A' to apply,\n      'E' to submit enquiry\nEnter anything else to go back: ");
        selection = sc.nextLine().trim().toLowerCase();
        if (selection.equals("e")) {
            submitEnquiry(user.getNric());
        } else if (selection.equals("a")) {
            applyForProject();
        }
    }

    private void displayAppliedProjects() {
        List<ProjectApplication> applications =
                ProjectApplicationController.getApplicationsByApplicantID(user.getNric());

        if (applications.isEmpty()) {
            System.out.println("No projects applied.");
            return;
        }

        System.out.println("\nYou have applied to the following project" + (applications.size() > 1 ? "s:" : ":"));
        for (ProjectApplication app : applications) {
            Project p = ProjectController.getProjectByID(app.getProjectID());
            printProjectForApplicant(user, p);
            System.out.println("Manager-in-charge: " + ManagerController.getNameById(p.getManagerID()));
            System.out.println("Status: " + app.getStatus());
        }
    }


    public void applyForProject() {
        Scanner sc = new Scanner(System.in);
        if (!ProjectApplicationController.checkPreviousApplication(user.getNric())) {
            System.out.println("You have already submitted an Application Before.");
            return;
        }
        if (user.getAge() < 21 || (user.getMaritalStatus() == MaritalStatus.SINGLE && user.getAge() < 35)) {
            System.out.println("You do not meet the age requirements.");
            return;
        }

        List<Project> validProjects = ProjectController.getProjectsForApplicant(user);
        if (validProjects.isEmpty()) {
            System.out.println("No Projects available for applications at the moment.");
            return;
        }

        System.out.println("\n=== Project Application ===");
        List<String> ValidprojIDs = ProjectController.getProjectIDsForApplicant(user);
        System.out.println("ID: " + ValidprojIDs);
        String projectID = SafeScanner.getValidatedStringInput(sc, "Enter Project ID you want to apply for: ", ValidprojIDs);
        Project p = ProjectController.getProjectByID(projectID);

        String roomType;
        if (user.getMaritalStatus() == MaritalStatus.SINGLE) {
            roomType = "2-room";
            p.prettyPrint4SingleApplicant();
        } else {
            p.prettyPrint4MarriedApplicant();
            roomType = SafeScanner.getValidatedStringInput(sc, "\nEnter a room type (2-Room or 3-Room): ", List.of("2-room", "3-room"));
        }


        if (!validateRoomAvailability(p, roomType)) return;

        if (!ProjectApplicationController.createProjectApplication(projectID.toUpperCase(), roomType, user.getNric())) {
            System.out.println("Project application could not be created.");
        } else {
            System.out.println("Application created successfully!");
        }
    }

    private boolean validateRoomAvailability(Project p, String roomType) {
        if (roomType.equalsIgnoreCase("2-room") &&
                ((p.getType1().equalsIgnoreCase("2-room") && p.getNoOfUnitsType1() < 1)
                        || (p.getType2().equalsIgnoreCase("2-room") && p.getNoOfUnitsType2() < 1))) {
            System.out.println("No 2-room units available for this project.");
            return false;
        }
        if (roomType.equalsIgnoreCase("3-room") &&
                ((p.getType1().equalsIgnoreCase("3-room") && p.getNoOfUnitsType1() < 1)
                        || (p.getType2().equalsIgnoreCase("3-room") && p.getNoOfUnitsType2() < 1))) {
            System.out.println("No 3-room units available for this project.");
            return false;
        }
        return true;
    }

    public void viewApplication() {
        List<ProjectApplication> failApp = ProjectApplicationController.getUnsuccessApplicationByApplicantID(user.getNric());
        ProjectApplication currentApp = ProjectApplicationController.getCurrentApplicationByApplicantID(user.getNric());

        if (failApp.isEmpty() && currentApp == null) {
            System.out.println("Application could not be found.");
            return;
        }

        if (!failApp.isEmpty()) {
            System.out.println("\n________Unsuccessful Applications________");
            for (ProjectApplication app : failApp) {
                app.prettyPrintApplicant();
            }
        }

        if (currentApp != null) {
            System.out.println("\n__________Current Application__________");
            currentApp.prettyPrintApplicant();
            Project p = ProjectController.getProjectByID(currentApp.getProjectID());
            System.out.println("\n________Project_________");
            if (currentApp.getRoomType().equalsIgnoreCase("2-room")) {
                p.prettyPrint4SingleApplicant();
            } else {
                p.prettyPrintApplicant3room();
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("\nEnter W to withdraw application,\nEnter anything else to go back: ");
            if (sc.nextLine().trim().equalsIgnoreCase("W")) {
                withdrawApplication();
            }
        }
    }

    private void withdrawApplication() {
        ProjectApplication application = ProjectApplicationController.getCurrentApplicationByApplicantID(user.getNric());
        if (application == null) {
            System.out.println("No application to withdraw.");
            return;
        }
        String confirm = SafeScanner.getValidatedStringInput(new Scanner(System.in), "Are you sure you want to withdraw? (y/n): ", List.of("y", "n"));
        if (confirm.equalsIgnoreCase("y")) {
            boolean success = ProjectApplicationController.withdrawApplication(user.getNric());
            System.out.println(success ? "Application withdrawn." : "Withdrawal failed.");
        }
    }

    private void displayProjectFilterMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n=== Project Filter Menu ===");
            System.out.println("1. Neighbourhood: " + filterContext.neighbourhood);
            System.out.println("2. Flat Type: " + filterContext.flatType);
            System.out.println("3. Reset Filters");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 3);
            switch (choice) {
                case 1 -> {
                    filterContext.neighbourhood = SafeScanner.getValidatedStringInput(sc, "Enter the neighbourhood: ", 100);
                    filterContext.neighbourhoodFilter = p -> p.getNeighbourhood().equalsIgnoreCase(filterContext.neighbourhood);
                }
                case 2 -> {
                    filterContext.flatType = SafeScanner.getValidatedStringInput(sc, "Enter flat type (2-Room/3-Room):", List.of("2-room", "3-room"));
                    filterContext.flatTypeFilter = p ->
                            (p.getType1().equalsIgnoreCase(filterContext.flatType) && p.getNoOfUnitsType1() > 0)
                                    || (p.getType2().equalsIgnoreCase(filterContext.flatType) && p.getNoOfUnitsType2() > 0);
                }
                case 3 -> {
                    filterContext.reset();
                    System.out.println("Filters reset.");
                }
            }
            filterContext.updateFilter();
        } while (choice != 0 && choice != 3);
    }

    private void printProjectForApplicant(IUserProfile user, Project proj) {
        if (user.getMaritalStatus() == MaritalStatus.SINGLE) {
            proj.prettyPrint4SingleApplicant();
        } else if (filterContext.flatType == null) {
            proj.prettyPrint4MarriedApplicant();
        } else if ("3-room".equalsIgnoreCase(filterContext.flatType)) {
            proj.prettyPrintApplicant3room();
        } else {
            proj.prettyPrint4SingleApplicant();
        }
    }

    // Add back any applicant-only functions here like viewApplicantProfile, enquiry menus, etc...
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


    //User profile functions
    public void viewApplicantProfile(Applicant applicant) {
        Scanner sc = new Scanner(System.in);
        String selection;
        do {
            Utils.PrettyPrint.prettyPrint(applicant);

            List<String> validOptions = Arrays.asList("y", "n");
            selection = SafeScanner.getValidatedStringInput(sc,"Would you like to update your profile?\nEnter: y/n\n",validOptions);
            if (selection.equals("y")) {
                updateApplicantProfile(applicant);
            }
        }while(!selection.equals("n"));

    }

    public void updateApplicantProfile(Applicant applicant) {
        int choice;
        Scanner sc = new Scanner(System.in);
        do{
            Utils.PrettyPrint.prettyUpdate(applicant);

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 3);

            switch (choice) {
                case 1 -> updateName(applicant);
                case 2 -> updateAge(applicant);
                case 3 -> updateMaritalStatus(applicant);
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);

    }


    private void updateAge(Applicant applicant) {
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

    private void updateName(Applicant applicant) {
        Scanner sc = new Scanner(System.in);
        String newName = SafeScanner.getValidatedStringInput(sc,"Enter your new Name: ",50);
        if(ApplicantController.updateName(applicant, newName)){
            System.out.println("Your name has been updated!\n");
        }
        else{
            System.out.println("Update failed, try again later\n");
        }
    }

    private void updateMaritalStatus(Applicant applicant) {
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




}
