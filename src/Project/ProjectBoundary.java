package Project;

import Manager.Manager;
import Utils.SafeScanner;

import java.util.ArrayList;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ProjectBoundary {
    static Scanner sc = new Scanner(System.in);
    private static Manager manager;
    public ProjectBoundary(Manager manager) {
        ProjectBoundary.manager = manager;
    }
    public static void displayProjectMenu() {
        int choice;
        do {
            System.out.println("\n=== Project Menu ===");
            System.out.println("1. Display Current Projects");
            System.out.println("2. Create New Project");
            System.out.println("3. Edit Project");
            System.out.println("4. Delete Project");
            System.out.println("0. Back to Manager Menu");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> displayProjects();
                case 2 -> createNewProject(manager.getName());
                case 3 -> projectChangesMenu();
                case 4 -> deleteProject();
                case 0 -> System.out.println("Exiting the Project Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0);
    }

    public static void displayProjects() {
        ArrayList<Project> projects = ProjectController.getAllProjects();
        if (projects.isEmpty()) {
            System.out.println("No Projects Available.");
        } else {
            for (Project project : projects) {
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
                System.out.println("Number of Officer Slot(s): "+project.getNoOfficersSlots());
                System.out.println("Officer(s) Assigned: ");
                String[] officers = project.getOfficer();
                for (String officer : officers) {
                    System.out.println(officer);
                }
                System.out.println("------------------------");

            }
        }
    }

    public static void createNewProject(String manager_name) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean isValidOpenDate = false, isValidCloseDate = false;
        String appDateOpen = "", appDateClose = "";
        System.out.println("\n=== Project Creator ===");

        String projectID = ProjectController.generateUniqueProjectID();

        System.out.print("Enter Project Name: ");
        String projectName = sc.nextLine().trim();
        while(!ProjectController.checkUniqueProjectName(projectName)){
            System.out.print("Project Name exists in database, please enter a valid project name: ");
            projectName = sc.nextLine().trim();
        }

        System.out.print("Enter Neighbourhood: ");
        String neighbourhood = sc.nextLine().trim();

        System.out.print("Enter Room Type 1: ");
        String roomType1 = sc.nextLine().trim();

        int noOfUnitsType1 = SafeScanner.getValidatedIntInput(sc, "Enter Number Of Units for Room Type 1: ", 0, 1000);
        int sellPriceType1 = SafeScanner.getValidatedIntInput(sc, "Enter Selling Price for Room Type 1: ", 0, 10000000);

        System.out.print("Enter Room Type 2: ");
        String roomType2 = sc.nextLine().trim();

        int noOfUnitsType2 = SafeScanner.getValidatedIntInput(sc, "Enter Number Of Units for Room Type 2: ", 0, 1000);
        int sellPriceType2 = SafeScanner.getValidatedIntInput(sc, "Enter Selling Price for Room Type 2: ", 0, 10000000);

        LocalDate dateOpen = null;
        while (!isValidOpenDate) {
            System.out.print("Enter Application Opening Date (DD/MM/YYYY): ");
            appDateOpen = sc.nextLine().trim();
            try {
                dateOpen = LocalDate.parse(appDateOpen, dateFormatter);
                isValidOpenDate = true; // Set to true if parsing is successful
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a valid date (DD/MM/YYYY).");
            }
        }

        LocalDate dateClose = null;
        while (!isValidCloseDate) {
            System.out.print("Enter Application Closing Date (DD/MM/YYYY): ");
            appDateClose = sc.nextLine().trim();
            try {
                dateClose = LocalDate.parse(appDateOpen, dateFormatter);
                isValidCloseDate = true; // Set to true if parsing is successful
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a valid date (DD/MM/YYYY).");
            }
        }

        int noOfficersSlots = SafeScanner.getValidatedIntInput(sc, "Enter Number of Officer Slots: ", 0, 100);

        System.out.print("Enter Officers (comma-separated): ");
        String officersInput = sc.nextLine().trim();
        String[] officers = officersInput.split(",");
        LocalDate currentDate = LocalDate.now();

        boolean visible;
        visible = !currentDate.isBefore(dateOpen) && !currentDate.isAfter(dateClose);

        // Create a new project instance
        Project newProject = new Project(projectID,projectName, neighbourhood, roomType1, noOfUnitsType1,
                sellPriceType1, roomType2, noOfUnitsType2, sellPriceType2,
                appDateOpen, appDateClose, manager_name, noOfficersSlots, officers,visible);

        // Delegate the creation process to the ProjectController
        if (ProjectController.createProject(newProject)) {
            System.out.println("Project created successfully.");
        } else {
            System.out.println("Failed to create project. It might already exist.");
            }
    }

    public static void projectChangesMenu() {
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
            System.out.println("9. Update Maximum HDB Officer Slots");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 9);

            switch (choice) {
                case 1 -> updateProjectName();
                case 2 -> updateProjectNeighbourhood();
                case 3 -> updateProjectRoomType();
                case 4 -> updateProjectNumOfUnit();
                case 5 -> updateSellingPrice();
                case 6 -> updateProjectApplicationOpen();
                case 7 -> updateProjectApplicationClose();
                case 8 -> updateManagerInCharge();
                case 9 -> updateNumberOfOfficers();
                case 0 -> System.out.println("Exiting........");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0);
    }

    public static void deleteProject() {
        System.out.println("\n=== Deleting Project ===");
        System.out.print("Please enter the Project ID to delete: ");
        String projectID = sc.nextLine();
        boolean deleted = ProjectController.deleteProject(projectID);
        if (deleted) {
            System.out.println("Project deleted successfully.");
        } else {
            System.out.println("Deletion failed or Project not found.");
        }
    }

    public static void updateProjectName() {
        String projectID;
        String newProjectName;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        System.out.println("Please enter the new Project Name:");
        newProjectName = sc.nextLine();
        ProjectController.updateProjectName(projectID, newProjectName);
    }

    public static void updateProjectNeighbourhood() {
        String projectID;
        String newNeighbourhood;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        System.out.println("Please enter the New Neighbourhood:");
        newNeighbourhood = sc.nextLine();
        ProjectController.updateProjectNeighbourhood(projectID, newNeighbourhood);
    }

    public static void updateProjectRoomType() {
        String projectID;
        String roomType;
        int choice;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        Project project = ProjectController.getProjectByID(projectID);
        if (project == null) {
            System.out.println("Project not found with ID: " + projectID);
            return;
        }
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
                ProjectController.updateProjectRoomType1(projectID, roomType);
            }
            case 2 -> {
                System.out.println("Changing room type 2 to:");
                roomType = sc.nextLine();
                ProjectController.updateProjectRoomType2(projectID, roomType);
            }
            case 0 -> System.out.println("Exiting.....");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    public static void updateProjectNumOfUnit() {
        String projectID;
        int numOfUnits;
        int choice;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        Project project = ProjectController.getProjectByID(projectID);
        if (project == null) {
            System.out.println("Project not found with ID: " + projectID);
            return;
        }
        System.out.println("Current Room Types Availability:");
        System.out.println("1. " + project.getType1() + " Number of Units: "+ project.getNoOfUnitsType1());
        System.out.println("2. " + project.getType2() + " Number of Units: "+ project.getNoOfUnitsType2());
        System.out.println("0. Exit");
        System.out.println("Please select an Option:");
        choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 2);
        switch (choice) {
            case 1 -> {
                System.out.println("Changing number of room type 1 to:");
                numOfUnits = sc.nextInt();
                sc.nextLine();
                ProjectController.updateProjectNumOfRoomType1(projectID, numOfUnits);
            }
            case 2 -> {
                System.out.println("Changing number of room type 2 to:");
                numOfUnits = sc.nextInt();
                sc.nextLine();
                ProjectController.updateProjectNumOfRoomType2(projectID, numOfUnits);
            }
            case 0 -> System.out.println("Exiting.....");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    public static void updateSellingPrice(){
        String projectID;
        int sellPrice;
        int choice;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        Project project = ProjectController.getProjectByID(projectID);
        if (project == null) {
            System.out.println("Project not found with ID: " + projectID);
            return;
        }
        System.out.println("Current Room Types Selling Prices:");
        System.out.println("1. " + project.getType1() + " Selling Price: "+ project.getSellPriceType1());
        System.out.println("2. " + project.getType2() + " Selling Price: "+ project.getSellPriceType2());
        System.out.println("0. Exit");
        System.out.println("Please select an Option:");
        choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 2);
        switch (choice) {
            case 1 -> {
                System.out.println("Changing Selling Price of room type 1 to:");
                sellPrice = sc.nextInt();
                sc.nextLine();
                ProjectController.updateSellPriceOfRoomType1(projectID, sellPrice);
            }
            case 2 -> {
                System.out.println("Changing Selling Price of room type 2 to:");
                sellPrice = sc.nextInt();
                sc.nextLine();
                ProjectController.updateSellPriceOfRoomType2(projectID, sellPrice);
            }
            case 0 -> System.out.println("Exiting.....");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    public static void updateProjectApplicationOpen(){
        String projectID;
        String newOpenDate;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("The Current Opening Date is :"+ project.getAppDateOpen());
        System.out.println("Please enter the New Application Opening Date in DD/MM/YY format:");
        newOpenDate = sc.nextLine();
        ProjectController.updateProjectApplicationOpenDate(projectID, newOpenDate);
    }

    public static void updateProjectApplicationClose(){
        String projectID;
        String newCloseDate;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("The Current Closing Date is :"+ project.getAppDateClose());
        System.out.println("Please enter the New Application Closing Date in DD/MM/YY format:");
        newCloseDate = sc.nextLine();
        ProjectController.updateProjectApplicationCloseDate(projectID,newCloseDate);
    }

    public static void updateManagerInCharge(){
        String projectID;
        String newManager;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("The Current Manager is : "+ project.getManager());
        System.out.println("Please enter the New Manager:");
        newManager = sc.nextLine();
        ProjectController.updateProjectManager(projectID, newManager);
    }

    public static void updateNumberOfOfficers(){
        String projectID;
        int newNumberOfOfficers;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        Project project = ProjectController.getProjectByID(projectID);
        System.out.println("The Current Number of Officers Slots is : "+ project.getNoOfficersSlots());
        System.out.println("Please enter the New Number of Officer Slots:");
        newNumberOfOfficers = sc.nextInt();
        sc.nextLine();
        ProjectController.updateProjectNumOfOfficerSlots(projectID, newNumberOfOfficers);
    }
}

