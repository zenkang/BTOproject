package Manager;


import Applicant.ApplicantController;
import Enquiry.Enquiry;
import Enumerations.ApplicationStatus;
import Enumerations.RegistrationStatus;
import Officer.Officer;
import Officer.OfficerController;
import Project.Project;
import Project.ProjectController;
import Applicant.Applicant;

import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;
import ProjectRegistration.ProjectRegistration;
import Reply.ReplyController;
import User.UserBoundary;
import Enquiry.EnquiryController;
import Utils.PrettyPrint;
import Utils.SafeScanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ProjectRegistration.ProjectRegistrationController;

import Utils.PredicateUtils;

import static Utils.RepositoryGetter.*;


public class ManagerBoundary {
    private Manager manager;
    private Predicate<Project> Filter = null;
    private Predicate<Project> neighbourhoodFilter = null;
    private Predicate<Project> flatTypeFilter = null;
    private Predicate<Project>visibilityFilter = null;
    private String Filterneighbourhood = null;
    private String FilterflatType = null;
    private String FiltervisibilityChoice  = null;
    static Scanner sc = new Scanner(System.in);

    public ManagerBoundary(Manager manager) {
        this.manager = manager;

    }

    public void displayManagerMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n=== Manager Menu ===");
            System.out.println("1. View/update my profile");
            System.out.println("2. View Projects Menu");
            System.out.println("3. View Project Applications");
            System.out.println("4. View Project Registration Menu");
            System.out.println("5. View Enquiry Menu");
            System.out.println("6. Generate Report");
            System.out.println("7. Change Password");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 7);

            switch (choice) {
                case 1 -> viewProfile();
                case 2 -> displayProjectMenu();
                case 3 -> viewApplicantApplications();
                case 4 -> viewProjectRegistrationMenu();
                case 5 -> managerEnquiryMenu(sc, manager);
                case 6 -> System.out.println("Generate Report Function");
                case 7 -> UserBoundary.changePassword(manager.getUserProfile());
                case 0 -> System.out.println("Exiting the Manager Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0 && choice != 7);
        if (choice == 0) {
            sc.close();
        }
    }



    public void displayProjectMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n=== Project Menu ===");
            System.out.println("1. Display Projects");
            System.out.println("2. Create New Project");
            System.out.println("3. Edit Project");
            System.out.println("4. Update Filters");
            System.out.println("0. Back to Manager Menu");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> promptProjectViewChoice(manager.getID());
                case 2 -> createNewProject(manager.getID(), sc);
                case 3 -> projectChangesMenu(sc);
                case 4 -> displayProjectFilterMenu();
                case 0 -> System.out.println("Exiting the Project Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);
    }

    public void createNewProject(String managerID, Scanner sc) {
        int noOfUnitsType2, noOfUnitsType1;
        double sellPriceType2, sellPriceType1;
        boolean canCreateNewProject = ProjectController.checkActiveProject(managerID);
        if (canCreateNewProject) {
            System.out.println("\n=== Project Creator ===");

            String projectName = SafeScanner.getValidatedStringInput(sc,"Enter Project Name: ",100);
            while (!ProjectController.checkUniqueProjectName(projectName)) {
                System.out.print("Project Name exists in database, please enter a valid project name: ");
                projectName = SafeScanner.getValidatedStringInput(sc,"Enter Project Name: ",100);
            }

            String neighbourhood = SafeScanner.getValidatedStringInput(sc,"Enter Neighbourhood: ",100);


            String roomType1 = "2-Room";
            String roomType2 = "3-Room";
            System.out.println("Default room types: 2-Room and 3-Room\nEnter 'update' to update,  anything else to continue...");

            String selection = sc.nextLine().trim();
            if (selection.equals("update")) {
                roomType2 = SafeScanner.getValidatedStringInput(sc,"Enter Room Type 2: ",10);
                roomType1 = SafeScanner.getValidatedStringInput(sc,"Enter Room Type 1: ",10);
            }



            System.out.println(roomType1+" : ");
            noOfUnitsType1 = SafeScanner.getValidatedIntInput(sc, "Enter Number Of Units : ", 0, 10000);
            sellPriceType1 = SafeScanner.getValidatedDoubleInput(sc, "Enter Selling Price : ", 0.0, 1000000.0);

            System.out.println(roomType2+" : ");
            noOfUnitsType2 = SafeScanner.getValidatedIntInput(sc, "Enter Number Of Units : ", 0, 10000);
            sellPriceType2 = SafeScanner.getValidatedDoubleInput(sc, "Enter Selling Price : ", 0.0, 10000000.0);


            LocalDate dateOpen = SafeScanner.getValidDate(sc, "Enter Application Opening Date (DD/MM/YYYY): ");

            LocalDate dateClose = SafeScanner.getValidDateAfterDate(sc, dateOpen, "Enter Application Closing Date (DD/MM/YYYY): ");

            int noOfficersSlots = SafeScanner.getValidatedIntInput(sc, "Enter Number of Officer Slots: ", 0, 10);

            LocalDate currentDate = LocalDate.now();

            boolean visible;
            visible = !currentDate.isBefore(dateOpen) && !currentDate.isAfter(dateClose) && (noOfUnitsType2 > 0 || noOfUnitsType1 > 0);

            // Create a new project instance
            // Delegate the creation process to the ProjectController
            if (ProjectController.createProject(projectName, neighbourhood, roomType1, noOfUnitsType1, sellPriceType1, roomType2, noOfUnitsType2, sellPriceType2, dateOpen, dateClose, managerID, noOfficersSlots, visible)) {
                System.out.println("Project created successfully.");
            } else {
                System.out.println("Failed to create project. It might already exist.");
            }
        } else {
            System.out.println("You are unable to create a Project as you have an active Project.");
        }


    }

    public void projectChangesMenu(Scanner sc) {
        String projectID = SafeScanner.getValidatedStringInput(sc,"Please enter the Project ID: ",6);
        while (ProjectController.getProjectByID(projectID) == null) {
            System.out.println("Invalid Project ID. Please enter a valid Project ID.");
            projectID = SafeScanner.getValidatedStringInput(sc,"Please enter the Project ID: ",6);
        }

        int choice;
        do {
            System.out.println("\n=== Project Menu ===");
            System.out.println("1. Update Project Name");
            System.out.println("2. Update Neighbourhood");
            System.out.println("3. Update Types of Flat");
            System.out.println("4. Update Number of Units");
            System.out.println("5. Update Selling Price of Units");
            System.out.println("6. Update Application Opening Date");
            System.out.println("7. Update Application Closing Date");
            System.out.println("8. Update Visibility");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 8);

            switch (choice) {
                case 1 -> updateProjectName(sc,projectID);
                case 2 -> updateProjectNeighbourhood(sc,projectID);
                case 3 -> updateProjectRoomType(sc,projectID);
                case 4 -> updateProjectNumOfUnit(sc,projectID);
                case 5 -> updateSellingPrice(sc,projectID);
                case 6 -> updateProjectApplicationOpen(sc,projectID);
                case 7 -> updateProjectApplicationClose(sc,projectID);
                case 9 -> updateProjectVisibility(sc,projectID);
                case 0 -> System.out.println("Exiting........");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);
    }

    private void updateProjectVisibility(Scanner sc,String projectID) {
        String choice;
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println(project.getProjectName() + " visibility: " + ((project.isVisibility()) ? "True (Visible)" : "False (Not Visible)"));
        choice = SafeScanner.getValidatedStringInput(sc, "\nEnter new visibility: true(visible) or false(not visible)", Arrays.asList("true", "false"));
        if (project.isVisibility()) {
            if (choice.equalsIgnoreCase("true")) {
                System.out.println("Project visibility updated successfully.");
                return;
            } else if (choice.equalsIgnoreCase("false")) {
                if (ProjectController.updateProjectVisibility(project, false)) {
                    System.out.println("Project visibility updated successfully.");
                    return;
                }
            }
        } else {
            if (choice.equalsIgnoreCase("true")) {
                if (LocalDate.now().isAfter(project.getAppDateClose())) {
                    System.out.println("Warning: Project has been closed, adjust application closing date to toggle visibility on");
                    System.out.println("Today's date: " + LocalDate.now());
                    LocalDate newCloseDate = SafeScanner.getValidDateAfterDate(sc, project.getAppDateOpen(), "Please enter the New Application Closing Date in DD/MM/YYYY format: ");
                    if (ProjectController.updateProjectApplicationCloseDate(projectID, newCloseDate)) {
                        System.out.println("Project Application Closing Date updated successfully.");
                    }
                }
                if (ProjectController.updateProjectVisibility(project, true)) {
                    System.out.println("Project visibility updated successfully.");
                    return;
                }
            } else {
                System.out.println("Project visibility updated successfully.");
                return;
            }
        }
        System.out.println("update visibility failed");
    }

    public void updateProjectName(Scanner sc, String projectID) {
        String newProjectName = SafeScanner.getValidatedStringInput(sc,"Please enter the new Project Name:",100);
        while (!ProjectController.checkUniqueProjectName(newProjectName)) {
            System.out.print("Project Name exists in database, please enter a valid project name: ");
            newProjectName = sc.nextLine().trim();
        }
        if (ProjectController.updateProjectName(projectID, newProjectName)) {
            System.out.println("Project name updated successfully.");
        } else {
            System.out.println("Update failed.");
        }
    }

    public void updateProjectNeighbourhood(Scanner sc, String projectID) {
        String newNeighbourhood = SafeScanner.getValidatedStringInput(sc,"Please enter a new Neighbourhood:",100);
        if (ProjectController.updateProjectNeighbourhood(projectID, newNeighbourhood)) {
            System.out.println("Project Neighbourhood updated successfully.");
        } else {
            System.out.println("Update failed.");
        }
    }

    public void updateProjectRoomType(Scanner sc, String projectID) {
        String roomType;
        int choice;
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("Current Room Types:");
        System.out.println("1. " + project.getType1());
        System.out.println("2. " + project.getType2());
        System.out.println("0. Exit");
        choice = SafeScanner.getValidatedIntInput(sc, "Please select an Option:", 0, 2);
        switch (choice) {
            case 1 -> {
                System.out.println("Changing room type 1 to:");
                roomType = sc.nextLine();
                if (ProjectController.updateProjectRoomType1(projectID, roomType)) {
                    System.out.println("Project Room Type 1 updated successfully.");
                } else {
                    System.out.println("Update failed.");
                }
            }
            case 2 -> {
                System.out.println("Changing room type 2 to:");
                roomType = sc.nextLine();
                if (ProjectController.updateProjectRoomType2(projectID, roomType)) {
                    System.out.println("Project Room Type 2 updated successfully.");
                } else {
                    System.out.println("Update failed.");
                }
            }
            case 0 -> System.out.println("Exiting.....");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    public void updateProjectNumOfUnit(Scanner sc,String projectID) {
        int numOfUnits;
        int choice;
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("Current Room Types Availability:");
        System.out.println("1. " + project.getType1() + " Number of Units: " + project.getNoOfUnitsType1());
        System.out.println("2. " + project.getType2() + " Number of Units: " + project.getNoOfUnitsType2());
        System.out.println("0. Exit");
        System.out.println("Please select an Option:");
        choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 2);
        switch (choice) {
            case 1 -> {
                numOfUnits = SafeScanner.getValidatedIntInput(sc, "Changing number of units for room type 1 to:", 0, 1000);
                if (ProjectController.updateProjectNumOfRoomType1(projectID, numOfUnits)) {
                    System.out.println("Project Number of Room Type 1 updated successfully.");
                } else {
                    System.out.println("Update failed.");
                }
            }
            case 2 -> {
                numOfUnits = SafeScanner.getValidatedIntInput(sc, "Changing number of units for room type 2 to:", 0, 1000);
                if (ProjectController.updateProjectNumOfRoomType2(projectID, numOfUnits)) {
                    System.out.println("Project Number of Number of Room Type 2 updated successfully.");
                } else {
                    System.out.println("Update failed.");
                }
            }
            case 0 -> System.out.println("Exiting.....");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    public void updateSellingPrice(Scanner sc, String projectID) {
        double sellPrice;
        int choice;
        Project project = ProjectController.getProjectByID(projectID);

        System.out.println("Current Room Types Selling Prices:");
        System.out.println("1. " + project.getType1() + " Selling Price: " + project.getSellPriceType1());
        System.out.println("2. " + project.getType2() + " Selling Price: " + project.getSellPriceType2());
        System.out.println("0. Exit");
        System.out.println("Please select an Option:");
        choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 2);
        switch (choice) {
            case 1 -> {
                sellPrice = SafeScanner.getValidatedDoubleInput(sc, "Changing Selling Price of room type 1 to:", 0.0, 10000000.0);
                if (ProjectController.updateSellPriceOfRoomType1(projectID, sellPrice)) {
                    System.out.println("Project Selling Price of Type 1 Rooms updated successfully.");
                } else {
                    System.out.println("Update failed.");
                }
            }
            case 2 -> {
                sellPrice = SafeScanner.getValidatedDoubleInput(sc, "Changing Selling Price of room type 2 to:", 0.0, 10000000.0);
                if (ProjectController.updateSellPriceOfRoomType2(projectID, sellPrice)) {
                    System.out.println("Project Selling Price of Type 2 Rooms updated successfully.");
                } else {
                    System.out.println("Update failed.");
                }
            }
            case 0 -> System.out.println("Exiting.....");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    public void updateProjectApplicationOpen(Scanner sc, String projectID) {
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("The Current Opening Date is :" + project.getAppDateOpen());
        LocalDate newOpenDate = SafeScanner.getValidDate(sc, "Please enter the New Application Opening Date in DD/MM/YYYY format: ");
        if (ProjectController.updateProjectApplicationOpenDate(projectID, newOpenDate)) {
            System.out.println("Project Application Open Date updated successfully.");
        } else {
            System.out.println("Update failed.");
        }
    }

    public void updateProjectApplicationClose(Scanner sc, String projectID) {
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("The Current Closing Date is :" + project.getAppDateClose());
        LocalDate newCloseDate = SafeScanner.getValidDateAfterDate(sc, project.getAppDateOpen(), "Please enter the New Application Closing Date in DD/MM/YYYY format: ");
        if (ProjectController.updateProjectApplicationCloseDate(projectID, newCloseDate)) {
            System.out.println("Project Application Closing Date updated successfully.");
        } else {
            System.out.println("Update failed.");
        }
    }

    public void viewProfile() {
        Scanner sc = new Scanner(System.in);
        String selection;
        do {
            PrettyPrint.prettyPrint(manager);

            List<String> validOptions = Arrays.asList("y", "n");
            selection = SafeScanner.getValidatedStringInput(sc, "Would you like to update your profile?\nEnter: y/n\n", validOptions);
            if (selection.equals("y")) {
                updateProfile();
            }
        } while (!selection.equals("n"));

    }

    public void updateProfile() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            PrettyPrint.prettyUpdate(manager);

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

    private void updateAge() {
        Scanner sc = new Scanner(System.in);
        int age;
        age = SafeScanner.getValidatedIntInput(sc, "Enter your new age: ", 0, 200);
        if (ManagerController.updateAge(manager, age)) {
            System.out.println("Your age has been updated!\n");
        } else {
            System.out.println("Update failed, try again later\n");
        }
    }

    private void updateName() {
        Scanner sc = new Scanner(System.in);
        String newName = SafeScanner.getValidatedStringInput(sc, "Enter your new Name: ", 50);
        if (ManagerController.updateName(manager, newName)) {
            System.out.println("Your name has been updated!\n");
        } else {
            System.out.println("Update failed, try again later\n");
        }
    }

    private void updateMaritalStatus() {
        Scanner sc = new Scanner(System.in);
        List<String> validOptions = Arrays.asList("m", "s");
        String maritalStatus = SafeScanner.getValidatedStringInput(sc, "Enter your marital status: ( m: Married , s: Single)\n", validOptions);
        if (ManagerController.updateMaritalStatus(manager, maritalStatus)) {
            System.out.println("Your marital status has been updated!\n");
        } else {
            System.out.println("Update failed, try again later\n");
        }
    }


    //project applications ***TO BE COMPLETED!!!
    private void viewApplicantApplications() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            int numRejected = ProjectApplicationController.getNumRejectedApplications(manager);

            int numPending = ProjectApplicationController.getNumPendingApplications(manager);
            System.out.println("\n=== Applications ===");
            System.out.println("1. View all Applications");
            System.out.println("2. View pending Applications " + ((numPending == 0) ? "" : "(" + numPending + ")"));
            System.out.println("3. View Filtered applications");
            System.out.println("4. Update Filters");
            if(numRejected > 0) {
                System.out.println("5. View withdrawn applications");
            }
            System.out.println("0. Back");

            if(numRejected > 0) {
                choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 5);
            }
            else {
                choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 4);
            }

            switch (choice) {
                case 1 -> ProjectApplicationController.displayAllProjectApplications(manager);
                case 2 -> {
                    List<ProjectApplication> list = ProjectApplicationController.getApplicationsByStatus(manager, ApplicationStatus.PENDING);
                    if (list.isEmpty()) {
                        System.out.println("No pending applications found.");
                    } else {
                        list.forEach(System.out::println);
                    }
                }
                case 3 -> System.out.println("TBC");
                case 4 -> System.out.println("TBC");
                case 5 -> viewWithdrawnApplication(manager);
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
            if (choice == 2 && numPending > 0) {
                List<String> validOptions = Arrays.asList("y", "n");
                String selection = SafeScanner.getValidatedStringInput(sc, "Approve application?\nEnter: y/n\n", validOptions);
                if (selection.equals("y")) {
                    List<String> validIDs = ProjectApplicationController.getPendingApplicationIDs(manager);
                    String applicationID = SafeScanner.getValidatedStringInput(sc, "Enter application ID: ", validIDs);
                    updateApplicationStatus(applicationID);
                }
            }
        } while (choice != 0);
    }

    private static void viewWithdrawnApplication(Manager manager) {
        List<ProjectApplication> projectApplicationList = ProjectApplicationController.getApplicationsByStatus(manager, ApplicationStatus.UNSUCCESSFUL);
        if (projectApplicationList.isEmpty()) {
            System.out.println("No withdrawn applications found.");
        }
        else {
            for (ProjectApplication projectApplication : projectApplicationList) {
                System.out.println(projectApplication);
            }
            List<String> validIDs = ProjectApplicationController.getUnsuccessfulApplicationIDs(manager);
            String applicationID = SafeScanner.getValidatedStringInput(sc, "Enter application ID to reject: ", validIDs);
            if(ProjectApplicationController.rejectWithdrawal(applicationID)){
                System.out.println("Application rejected.");
            }
            else{
                System.out.println("Application rejection unsuccessful.");
            }
        }

    }

    private static void updateApplicationStatus(String applicationID) {
        Scanner sc = new Scanner(System.in);
        ProjectApplication application = getProjectApplicationsRepository().getByID(applicationID);
        Applicant applicant = ApplicantController.getApplicantById(application.getApplicantID());
        if (applicant == null) {
            Officer officer = OfficerController.getApplicantById(application.getApplicantID());
            PrettyPrint.prettyPrint(officer);
        } else {
            PrettyPrint.prettyPrint(applicant);
        }

        List<String> validOptions = Arrays.asList("s", "u");
        String selection = SafeScanner.getValidatedStringInput(sc, "\nUpdated Status: (s : Successful, u : Unsuccessful) \n", validOptions);
        ApplicationStatus status = null;
        switch (selection.toLowerCase()) {
            case "s" -> status = ApplicationStatus.SUCCESSFUL;
            case "u" -> status = ApplicationStatus.UNSUCCESSFUL;
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
        if (ProjectApplicationController.updateApplicationStatus(application, status)) {
            if (status == ApplicationStatus.SUCCESSFUL) {
                System.out.println("Application Approved!\n");
            } else if (status == ApplicationStatus.UNSUCCESSFUL) {
                System.out.println("Application Rejected!\n");
            }
        } else {
            System.out.println("Update failed, try again later\n");
        }
    }

    public void displayProjectFilterMenu() {
        int choice;
        do {
            System.out.println("\n=== Project Filter Menu ===");
            System.out.println("1. Neighbourhood");
            System.out.println("2. Flat Type");
            System.out.println("3. visibility");
            System.out.println("4. Reset Filters");
            System.out.println("0. Exit");
            System.out.println("\nCurrent filters applied :\nNeighbourhood: "+Filterneighbourhood+" | Flat Type: "+FilterflatType+" | Visibility: "+ FiltervisibilityChoice);

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 4);

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
                case 3->{
                    FiltervisibilityChoice = SafeScanner.getValidatedStringInput(sc, "Enter the visibility : (t : True || f : False) :",Arrays.asList("t", "f") );
                    visibilityFilter = switch (FiltervisibilityChoice) {
                        case "y" -> project -> project.isVisibility();
                        case "n" -> project -> !project.isVisibility();
                        default -> project -> project.isVisibility();
                    };
                }
                case 4 -> {
                    Filter = null;
                    FilterflatType = null;
                    FiltervisibilityChoice = null;
                    Filterneighbourhood = null;
                    System.out.println("Filter Reset.");
                }
                case 0 -> {
                    System.out.println("Filter preferences updated.");
                    System.out.println("Exiting the Project Filter Menu.");
                }
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);
        Filter = PredicateUtils.combineFilters(neighbourhoodFilter, flatTypeFilter,visibilityFilter);
    }

    public static void displayFilteredProjects(Predicate<Project> Filter) {
        List<Project> filteredProjects = ProjectController.getFilteredProjects(Filter);
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects match your filter criteria.");
        } else {
            System.out.println("Filtered Projects:");
            for (Project project : filteredProjects) {
                project.prettyPrintManager();
            }
        }
    }

    public void promptProjectViewChoice(String managerID) {
        String selection = SafeScanner.getValidatedStringInput(sc, "View your projects? (y : yes || n : no) ", Arrays.asList("y", "n"));
        if (selection.equals("y")) {
            List<Project> managerProjects = ProjectController.getProjectsCreatedByManager(managerID);
            if(managerProjects.isEmpty()){
                System.out.println("\nNo projects created by you.");
            }
            else {
                System.out.println("\nYour Projects: \n");
                for (Project project : managerProjects) {
                    project.prettyPrintManager();
                }
            }
        } else {
            displayFilteredProjects(Filter);
        }

    }


    public static void managerEnquiryMenu(Scanner sc, Manager manager) {
        int choice;
        do {
            System.out.println("\n--- Enquiry Menu (Manager) ---");
            System.out.println("1. View All Enquiries");
            System.out.println("2. Reply to Enquiries for Your Projects");
            System.out.println("0. Back");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 2);

            switch (choice) {
                case 1 -> viewAllEnquiries();
                case 2 -> replyToOwnProjectEnquiries(manager);
                case 0 -> System.out.println("Going Back....");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);


    }

    private static void viewAllEnquiries() {
        List<Enquiry> enquiries = EnquiryController.getAllEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries available.");
            return;
        }

        System.out.println("\n--- All Enquiries ---");
        for (Enquiry e : enquiries) {
            System.out.println(e);
        }
    }

    private static void replyToOwnProjectEnquiries(Manager manager) {

        List<Enquiry> replyable = EnquiryController.getUnrepliedEnquiriesByProjects(manager.getID());

        if (replyable.isEmpty()) {
            System.out.println("No pending enquiries to reply to.");
            return;
        }

        System.out.println("\n--- Replyable Enquiries ---");
        for (int i = 0; i < replyable.size(); i++) {
            System.out.println((i + 1) + ". " + replyable.get(i));
        }

        System.out.print("Select an enquiry to reply to (0 to cancel): ");
        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to menu.");
            return;
        }

        if (choice == 0) {
            System.out.println("Reply cancelled.");
            return;
        }

        if (choice < 1 || choice > replyable.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Enquiry selected = replyable.get(choice - 1);
        System.out.print("Enter your reply: ");
        String replyContent = sc.nextLine();

        ReplyController.addReply(selected.getEnquiryId(), manager.getNric(), replyContent);

        System.out.println("Reply submitted and enquiry status updated.");
    }

    private void viewProjectRegistrationMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n--- View Project Registration Menu ---");
            System.out.println("1. View handled Project Registration");
            System.out.println("2. Approve/Reject Project Registration");
            System.out.println("0. Back");
            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 2);
            switch (choice) {
                case 1 -> displayAllHandledProjectRegistration(manager.getID());
                case 2 -> ApproveOrRejectProjectRegistration(manager.getID());
                case 0 -> System.out.println("Going Back....");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }while(choice !=0);

    }

    private static void ApproveOrRejectProjectRegistration(String id) {
        System.out.println("\n--- Approve or Reject Project Registration ---");
        List<ProjectRegistration> projectRegistrations = ProjectRegistrationController.getHandledProjectRegistration(id);
        if (projectRegistrations.isEmpty()) {
            System.out.println("No handling project registrations available.");
        }
        else{
            for (ProjectRegistration p : projectRegistrations) {
                p.prettyPrint();
            }
        }
        List<String> validRegisterIds = projectRegistrations.stream().map(ProjectRegistration::getRegistrationID).toList();
        String registerID = SafeScanner.getValidatedStringInput(sc,"Enter Registration ID you want to approve or reject for: ",validRegisterIds);
        List<String> validOptions = Arrays.asList("a", "r");
        String choice = SafeScanner.getValidatedStringInput(sc, "\nUpdated Status: (a : Approve, r : Reject) \n", validOptions);
        RegistrationStatus status = null;
        switch (choice.toLowerCase()) {
            case "a" -> status = RegistrationStatus.APPROVED;
            case "r" -> status = RegistrationStatus.REJECTED;
            default -> System.out.println("Invalid choice. Please select a valid option.");
            }
            if(ProjectRegistrationController.updateProjectRegistration(registerID,status)){
                if(status == RegistrationStatus.APPROVED){
                    ProjectController.updateOfficer(registerID);
                    System.out.println("Project registration approval is successful");
                }
                else{
                    System.out.println("Project registration reject is successful");
                }

            }
            else{
               System.out.println("Error");
            }



    }

    private static void displayAllHandledProjectRegistration(String id) {
        List<ProjectRegistration> projectRegistrations = ProjectRegistrationController.getHandledProjectRegistration(id);
        if (projectRegistrations.isEmpty()) {
            System.out.println("No handling project registrations available.");
        }
        else{
            for (ProjectRegistration p : projectRegistrations) {
                System.out.println("Registration ID: " + p.getRegistrationID());
                System.out.println("Project Name: " + p.getProjectName());
                System.out.println("Officer ID: " + p.getOfficerId());
                System.out.println("Status: " + p.getStatus());
            }
        }
    }
}
