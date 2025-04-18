package Manager;

import Applicant.ApplicantController;
import Enquiry.EnquiryManagerBoundary;
import Enumerations.ApplicationStatus;
import Project.Project;
import Project.ProjectController;
import Applicant.Applicant;

import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;
import User.UserBoundary;
import Utils.SafeScanner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import Utils.PredicateUtils;
import static Utils.RepositoryGetter.*;



public class ManagerBoundary {
    private Manager manager;
    private static Predicate<Project> Filter = null;
    private static Predicate<Project> neighbourhoodFilter = null;
    private static Predicate<Project> flatTypeFilter = null;
    static Scanner sc = new Scanner(System.in);

    public ManagerBoundary(Manager manager) {
        this.manager = manager;

    }

    public void displayMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n=== Manager Menu ===");
            System.out.println("1. View/update my profile");
            System.out.println("2. View Projects Menu");
            System.out.println("3. View project applications");
            System.out.println("4. View Project Registration Menu");
            System.out.println("5. View Enquiry Menu");
            System.out.println("6. Change Password");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 7);

            switch (choice) {
                case 1 -> viewProfile();
                case 2 -> displayProjectMenu();
                case 3 -> viewApplicantApplications();
                case 4 -> System.out.println("TBC");
                case 5 -> EnquiryManagerBoundary.managerMenu(manager);
                case 6 -> changePassword();
                case 0 -> System.out.println("Exiting the Manager Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0 && choice !=7) ;
        if (choice == 0){
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
            System.out.println("4. Delete Project");
            System.out.println("5. Update Filters");
            System.out.println("0. Back to Manager Menu");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 5);

            switch (choice) {
                case 1 -> promptProjectViewChoice(this.manager.getID()) ;
                case 2 -> createNewProject(this.manager.getID(), sc);
                case 3 -> projectChangesMenu(sc);
                case 4 -> deleteProject(sc);
                case 5 -> displayProjectFilterMenu();
                case 0 -> System.out.println("Exiting the Project Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0);
    }

    public static void createNewProject(String managerID, Scanner sc) {
        int noOfUnitsType2, noOfUnitsType1;
        double sellPriceType2, sellPriceType1;
        boolean canCreateNewProject = ProjectController.checkActiveProject(managerID);
        if (canCreateNewProject) {
            System.out.println("\n=== Project Creator ===");

            System.out.print("Enter Project Name: ");
            String projectName = sc.nextLine().trim();
            while (!ProjectController.checkUniqueProjectName(projectName)) {
                System.out.print("Project Name exists in database, please enter a valid project name: ");
                projectName = sc.nextLine().trim();
            }

            System.out.print("Enter Neighbourhood: ");
            String neighbourhood = sc.nextLine().trim();
            if (neighbourhood.isEmpty()) {
                System.out.println("Empty Neighbourhood. Please enter a valid input.");
            }

            System.out.print("Enter Room Type 1 in this format (2-Room): ");
            String roomType1 = sc.nextLine().trim();
            if (roomType1.isEmpty()) {
                System.out.println("Empty Room Type 1. Please enter a valid input.");

            }

            noOfUnitsType1 = SafeScanner.getValidatedIntInput(sc, "Enter Number Of Units for Room Type 1: ", 0, 1000);
            sellPriceType1 = SafeScanner.getValidatedDoubleInput(sc, "Enter Selling Price for Room Type 1: ", 0.0, 10000000.0);

            System.out.print("Enter Room Type 2 in this format (3-Room): ");
            String roomType2 = sc.nextLine().trim();
            if (roomType2.isEmpty()) {
                System.out.println("Empty Room Type 2. Please enter a valid input.");
            }
            noOfUnitsType2 = SafeScanner.getValidatedIntInput(sc, "Enter Number Of Units for Room Type 2: ", 0, 1000);
            sellPriceType2 = SafeScanner.getValidatedDoubleInput(sc, "Enter Selling Price for Room Type 2: ", 0.0, 10000000.0);


            LocalDate dateOpen = SafeScanner.getValidDate(sc, "Enter Application Opening Date (DD/MM/YYYY): ");

            LocalDate dateClose = SafeScanner.getValidDateAfterDate(sc, dateOpen, "Enter Application Closing Date (DD/MM/YYYY): ");

            int noOfficersSlots = SafeScanner.getValidatedIntInput(sc, "Enter Number of Officer Slots: ", 0, 10);

            System.out.print("Enter Officers (comma-separated): ");
            String officersInput = sc.nextLine().trim();
            String[] officers = officersInput.split(",");


            LocalDate currentDate = LocalDate.now();

            boolean visible;
            visible = !currentDate.isBefore(dateOpen) && !currentDate.isAfter(dateClose) && (noOfUnitsType2>0 || noOfUnitsType1>0);

            // Create a new project instance
            // Delegate the creation process to the ProjectController
            if (ProjectController.createProject(projectName, neighbourhood, roomType1, noOfUnitsType1, sellPriceType1, roomType2, noOfUnitsType2, sellPriceType2, dateOpen, dateClose, managerID, noOfficersSlots, officers, visible)) {
                System.out.println("Project created successfully.");
            } else {
                System.out.println("Failed to create project. It might already exist.");
            }
        } else {
            System.out.println("You are unable to create a Project as you have an active Project.");
        }


    }

    public static void projectChangesMenu(Scanner sc) {

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
            System.out.println("8. Update Manager in charge");
            System.out.println("9. Update Visibility");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 9);

            switch (choice) {
                case 1 -> updateProjectName(sc);
                case 2 -> updateProjectNeighbourhood(sc);
                case 3 -> updateProjectRoomType(sc);
                case 4 -> updateProjectNumOfUnit(sc);
                case 5 -> updateSellingPrice(sc);
                case 6 -> updateProjectApplicationOpen(sc);
                case 7 -> updateProjectApplicationClose(sc);
                case 8 -> updateManagerInCharge(sc);
                case 9 -> updateProjectVisibility(sc);
                case 0 -> System.out.println("Exiting........");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0);
    }

    private static void updateProjectVisibility(Scanner sc) {
        String projectID;
        String choice;
        List<String> validOptions = Arrays.asList("true", "false");
        System.out.println("Please enter the Project ID:");
        projectID = SafeScanner.getValidProjectID(sc);
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println(project.getProjectName()+ " visibility: " + ((project.isVisibility())? "True (Visible)" : "False (Not Visible)"));
        choice = SafeScanner.getValidatedStringInput(sc,"\nEnter new visibility: true(visible) or false(not visible)",validOptions);
        if(project.isVisibility()) {
            if (choice.equalsIgnoreCase("true")) {
                System.out.println("Project visibility updated successfully.");
                return;
            }
            else if(choice.equalsIgnoreCase("false")){
                if(ProjectController.updateProjectVisibility(project,false)){
                    System.out.println("Project visibility updated successfully.");
                    return;
                }
            }
        }
        else{
            if (choice.equalsIgnoreCase("true")){
                if(LocalDate.now().isAfter(project.getAppDateClose())){
                    System.out.println("Warning: Project has been closed, adjust application closing date to toggle visibility on");
                    System.out.println("Today's date: "+ LocalDate.now());
                    LocalDate newCloseDate = SafeScanner.getValidDateAfterDate(sc, project.getAppDateOpen(), "Please enter the New Application Closing Date in DD/MM/YYYY format: ");
                    if(ProjectController.updateProjectApplicationCloseDate(projectID, newCloseDate)){
                        System.out.println("Project Application Closing Date updated successfully.");
                    }
                }
                if(ProjectController.updateProjectVisibility(project,true)){
                    System.out.println("Project visibility updated successfully.");
                    return;
                }
            }
            else{
                System.out.println("Project visibility updated successfully.");
                return;
            }
        }
        System.out.println("update visibility failed");
    }

    public static void deleteProject(Scanner sc) {
        System.out.println("\n=== Deleting Project ===");
        System.out.print("Please enter the Project ID to delete: ");
        String projectID = sc.nextLine();
        while (ProjectController.getProjectByID(projectID) == null) {
            System.out.println("Invalid Project ID. Please enter a valid Project ID.");
            projectID = sc.nextLine();
        }
        boolean deleted = ProjectController.deleteProject(projectID);
        if (deleted) {
            System.out.println("Project deleted successfully.");
        } else {
            System.out.println("Deletion failed or Project not found.");
        }
    }

    public static void updateProjectName(Scanner sc) {
        String projectID;
        String newProjectName;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        while (ProjectController.getProjectByID(projectID) == null) {
            System.out.println("Invalid Project ID. Please enter a valid Project ID.");
            projectID = sc.nextLine();
        }
        System.out.println("Please enter the new Project Name:");
        newProjectName = sc.nextLine();
        while (!ProjectController.checkUniqueProjectName(newProjectName)) {
            System.out.print("Project Name exists in database, please enter a valid project name: ");
            newProjectName = sc.nextLine().trim();
        }
        if(ProjectController.updateProjectName(projectID, newProjectName)){
            System.out.println("Project name updated successfully.");
        }
        else{
            System.out.println("Update failed.");
        }
    }

    public static void updateProjectNeighbourhood(Scanner sc) {
        String projectID;
        String newNeighbourhood;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        while (ProjectController.getProjectByID(projectID) == null) {
            System.out.println("Invalid Project ID. Please enter a valid Project ID.");
            projectID = sc.nextLine();
        }
        System.out.println("Please enter the New Neighbourhood:");
        newNeighbourhood = sc.nextLine();
        if(ProjectController.updateProjectNeighbourhood(projectID, newNeighbourhood)){
            System.out.println("Project Neighbourhood updated successfully.");
        }
        else{
            System.out.println("Update failed.");
        }
    }

    public static void updateProjectRoomType(Scanner sc) {
        String projectID;
        String roomType;
        int choice;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        while (ProjectController.getProjectByID(projectID) == null) {
            System.out.println("Invalid Project ID. Please enter a valid Project ID.");
            projectID = sc.nextLine();
        }
        Project project = ProjectController.getProjectByID(projectID);

        System.out.println("Current Room Types:");
        System.out.println("1. " + project.getType1());
        System.out.println("2. " + project.getType2());
        System.out.println("0. Exit");
        System.out.println("Please select an Option:");
        choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 2);
        switch (choice) {
            case 1 -> {
                System.out.println("Changing room type 1 to:");
                roomType = sc.nextLine();
                if(ProjectController.updateProjectRoomType1(projectID, roomType)){
                    System.out.println("Project Room Type 1 updated successfully.");
                }
                else{
                    System.out.println("Update failed.");
                }
            }
            case 2 -> {
                System.out.println("Changing room type 2 to:");
                roomType = sc.nextLine();
                if(ProjectController.updateProjectRoomType2(projectID, roomType)){
                    System.out.println("Project Room Type 2 updated successfully.");
                }
                else{
                    System.out.println("Update failed.");
                }
            }
            case 0 -> System.out.println("Exiting.....");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    public static void updateProjectNumOfUnit(Scanner sc) {
        String projectID;
        int numOfUnits;
        int choice;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        while (ProjectController.getProjectByID(projectID) == null) {
            System.out.println("Invalid Project ID. Please enter a valid Project ID.");
            projectID = sc.nextLine();
        }
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
                if(ProjectController.updateProjectNumOfRoomType1(projectID, numOfUnits)){
                    System.out.println("Project Number of Room Type 1 updated successfully.");
                }
                else{
                    System.out.println("Update failed.");
                }
            }
            case 2 -> {
                numOfUnits = SafeScanner.getValidatedIntInput(sc, "Changing number of units for room type 2 to:", 0, 1000);
                if(ProjectController.updateProjectNumOfRoomType2(projectID, numOfUnits)){
                    System.out.println("Project Number of Number of Room Type 2 updated successfully.");
                }
                else{
                    System.out.println("Update failed.");
                }
            }
            case 0 -> System.out.println("Exiting.....");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    public static void updateSellingPrice(Scanner sc) {
        String projectID;
        double sellPrice;
        int choice;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        while (ProjectController.getProjectByID(projectID) == null) {
            System.out.println("Invalid Project ID. Please enter a valid Project ID.");
            projectID = sc.nextLine();
        }
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
                if(ProjectController.updateSellPriceOfRoomType1(projectID, sellPrice)){
                    System.out.println("Project Selling Price of Type 1 Rooms updated successfully.");
                }
                else{
                    System.out.println("Update failed.");
                }
            }
            case 2 -> {
                sellPrice = SafeScanner.getValidatedDoubleInput(sc, "Changing Selling Price of room type 2 to:", 0.0, 10000000.0);
                if(ProjectController.updateSellPriceOfRoomType2(projectID, sellPrice)){
                    System.out.println("Project Selling Price of Type 2 Rooms updated successfully.");
                }
                else{
                    System.out.println("Update failed.");
                }
            }
            case 0 -> System.out.println("Exiting.....");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    public static void updateProjectApplicationOpen(Scanner sc) {
        System.out.println("Please enter the Project ID:");
        String projectID = sc.nextLine();
        while (ProjectController.getProjectByID(projectID) == null) {
            System.out.println("Invalid Project ID. Please enter a valid Project ID.");
            projectID = sc.nextLine();
        }
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("The Current Opening Date is :" + project.getAppDateOpen());
        LocalDate newOpenDate = SafeScanner.getValidDate(sc, "Please enter the New Application Opening Date in DD/MM/YYYY format: ");
        if(ProjectController.updateProjectApplicationOpenDate(projectID, newOpenDate)){
            System.out.println("Project Application Open Date updated successfully.");
        }
        else{
            System.out.println("Update failed.");
        }
    }

    public static void updateProjectApplicationClose(Scanner sc) {
        System.out.println("Please enter the Project ID:");
        String projectID = sc.nextLine();
        while (ProjectController.getProjectByID(projectID) == null) {
            System.out.println("Invalid Project ID. Please enter a valid Project ID.");
            projectID = sc.nextLine();
        }
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("The Current Closing Date is :" + project.getAppDateClose());
        LocalDate newCloseDate = SafeScanner.getValidDateAfterDate(sc, project.getAppDateOpen(), "Please enter the New Application Closing Date in DD/MM/YYYY format: ");
        if(ProjectController.updateProjectApplicationCloseDate(projectID, newCloseDate)){
            System.out.println("Project Application Closing Date updated successfully.");
        }
        else{
            System.out.println("Update failed.");
        }
    }

    //Flaw -> what if you enter a Manager that does not exist!!
    public static void updateManagerInCharge(Scanner sc) {
        String projectID;
        String newManager;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        while (ProjectController.getProjectByID(projectID) == null) {
            System.out.println("Invalid Project ID. Please enter a valid Project ID.");
            projectID = sc.nextLine();
        }
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("The Current Manager is : " + project.getManagerID());
        System.out.println("Please enter the New Manager:");
        newManager = sc.nextLine();
        if(ProjectController.updateProjectManager(projectID, newManager)){
            System.out.println("Project Manager updated successfully.");
        }
        else{
            System.out.println("Update failed.");
        }
    }


    public void viewProfile() {
        Scanner sc = new Scanner(System.in);
        String selection;
        do {
            Utils.PrettyPrint.prettyPrint(manager);

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
            Utils.PrettyPrint.prettyUpdate(manager);

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
        UserBoundary.changePassword(manager.getUserProfile());
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
            int numPending = ProjectApplicationController.getNumPendingApplications(manager);
            System.out.println("\n=== Applications ===");
            System.out.println("1. View all Applications");
            System.out.println("2. View pending Applications " + ((numPending==0)? "" : "("+numPending+")" ));
            System.out.println("3. View Filtered applications");
            System.out.println("4. Update Filters");
            System.out.println("0. Back");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> ProjectApplicationController.displayAllProjectApplications(manager);
                case 2 -> {
                    List<ProjectApplication> list = ProjectApplicationController.getApplicationsByStatus(manager, ApplicationStatus.PENDING);
                    if(list.isEmpty()){
                        System.out.println("No pending applications found.");
                    }
                    else{
                        list.forEach(System.out::println);
                    }
                }
                case 3 -> System.out.println("TBC");
                case 4 -> System.out.println("TBC");
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
        }
        while (choice != 0);
    }

    private void updateApplicationStatus(String applicationID) {
        Scanner sc = new Scanner(System.in);
        ProjectApplication application = getProjectApplicationsRepository().getByID(applicationID);
        Applicant applicant = ApplicantController.getApplicantById(application.getApplicantID());
        Utils.PrettyPrint.prettyPrint(applicant);

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
            }
            else if (status == ApplicationStatus.UNSUCCESSFUL) {
                System.out.println("Application Rejected!\n");
            }
        } else {
            System.out.println("Update failed, try again later\n");
        }
    }

    public static void displayProjectFilterMenu() {
        int choice;
        do {
            System.out.println("\n=== Project Filter Menu ===");
            System.out.println("1. Neighbourhood");
            System.out.println("2. Flat Type");
            System.out.println("3. Combine both filters");
            System.out.println("4. Reset Filters");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> {
                    System.out.println("Enter the neighbourhood: ");
                    String neighbourhood = sc.nextLine();
                    neighbourhoodFilter = project -> project.getNeighbourhood().equalsIgnoreCase(neighbourhood);
                    Filter = neighbourhoodFilter;
                    ProjectController.getFilteredProjects(Filter);
                }
                case 2 -> {
                    List<String> validRoomOptions = Arrays.asList("2-room", "3-room");
                    String flatType = SafeScanner.getValidatedStringInput(sc, "Enter flat type filter(e.g.,2-Room,3-Room:)", validRoomOptions);
                    if (flatType.equals("2-room")) {
                        flatTypeFilter = project -> project.getType1().equalsIgnoreCase("2-room");
                        Filter = flatTypeFilter;
                        ProjectController.getFilteredProjects(Filter);
                    } else {
                        flatTypeFilter = project -> project.getType1().equalsIgnoreCase("3-room");
                        Filter = flatTypeFilter;
                        ProjectController.getFilteredProjects(Filter);
                    }
                }
                case 3 -> {
                    if (neighbourhoodFilter != null && flatTypeFilter != null) {
                        Filter = PredicateUtils.combineFilters(neighbourhoodFilter, flatTypeFilter);
                        ProjectController.getFilteredProjects(Filter);
                    } else {
                        System.out.println("Please set both the location filter and flat type filter before combining.");
                    }
                }
                case 4 -> {
                    Filter = null;
                    System.out.println("Filter Reset.");
                }
                case 0 -> {
                    System.out.println("Filter preferences updated.");
                    System.out.println("Exiting the Project Filter Menu.");
                }
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0);
    }

    public static void displayFilteredProjects(Predicate<Project> Filter) {
        List<Project> filteredProjects = ProjectController.getFilteredProjects(Filter);
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects match your filter criteria.");
        } else {
            System.out.println("Filtered Projects:");
            for (Project project : filteredProjects) {
                prettyPrintProjectDetails(project);
            }
        }
    }

    public static void promptProjectViewChoice(String managerName) {
        List<String> validOptions = Arrays.asList("yes", "no");
        String selection;
        selection = SafeScanner.getValidatedStringInput(sc, "Would you like to view projects created by you?\nEntering no will display all projects \nEnter yes or no: ", validOptions);
        if (selection.equals("yes")) {
            List<Project> managerProjects = ProjectController.getProjectsCreatedByManager(managerName);
            System.out.println("Projects created by you:");
            for (Project project : managerProjects) {
                prettyPrintProjectDetails(project);
            }
        } else {
            displayFilteredProjects(Filter);
        }

    }

    public static void prettyPrintProjectDetails(Project project){
        if(project==null){
            System.out.println("No project available.");
            return;
        }
        System.out.println("Project ID: "+project.getID());
        System.out.println("Project name: " + project.getProjectName());
        System.out.println("Neighbourhood: " + project.getNeighbourhood());
        System.out.println("Room Type 1: "+project.getType1());
        System.out.println("Number of units for Room Type 1: "+project.getNoOfUnitsType1());
        System.out.println("Selling price of Room Type 1: "+project.getSellPriceType1());
        System.out.println("Room Type 2: "+project.getType2());
        System.out.println("Number of units for Room Type 2: "+project.getNoOfUnitsType2());
        System.out.println("Selling price of Room Type 2: "+project.getSellPriceType2());
        System.out.println("Application Open Date: "+project.getAppDateOpen());
        System.out.println("Application Close Date: "+project.getAppDateClose());
        System.out.println("Manager-in-charge: "+project.getManagerID());
        System.out.println("Number of Officer Slot(s): "+project.getNoOfficersSlots());
        System.out.println("Officer(s) Assigned: ");
        String[] officers = project.getOfficer();
        for (String officer : officers) {
            System.out.println(officer);
        }
        System.out.println("Active Project: "+ project.isVisibility());
        System.out.println("------------------------");

    }

}
