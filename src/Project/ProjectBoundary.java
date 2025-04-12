package Project;

import Utils.SafeScanner;

import java.util.ArrayList;
import java.util.Scanner;


public class ProjectBoundary {
    Scanner sc = new Scanner(System.in);

    public void displayProjectMenu() {
        int choice;
        do {
            System.out.println("\n=== Project Menu ===");
            System.out.println("1. Display Current Projects");
            System.out.println("2. Create New Project");
            System.out.println("3. Edit Project");
            System.out.println("4. Delete Project");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> displayProjects();
                case 2 -> createNewProject();
                case 3 -> projectChangesMenu();
                case 4 -> deleteProject();
                case 0 -> System.out.println("Exiting the Project Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0);
    }

    public void displayProjects() {
        ArrayList<Project> projects = ProjectController.getAllProjects();
        if (projects.isEmpty()) {
            System.out.println("No Projects Available.");
        } else {
            for (Project project : projects) {
                System.out.println(project);
            }
        }
    }

    public void createNewProject() {
        System.out.println("\n=== Project Creator ===");
        System.out.println("Enter Project ID: ");
        String projectID = sc.nextLine().trim();
        if(projectID.isEmpty()){
            System.out.println("Empty ProjectID. Please enter a valid input.");
        }

        System.out.print("Enter Project Name: ");
        String projectName = sc.nextLine().trim();
        if(projectName.isEmpty()){
            System.out.println("Empty Project Name. Please enter a valid input.");
        }

        System.out.print("Enter Neighbourhood: ");
        String neighbourhood = sc.nextLine().trim();
        if(neighbourhood.isEmpty()){
            System.out.println("Empty Neighbourhood. Please enter a valid input.");
        }

        System.out.print("Enter Room Type 1: ");
        String roomType1 = sc.nextLine().trim();
        if(roomType1.isEmpty()){
            System.out.println("Empty Room Type 1. Please enter a valid input.");
        }

        int noOfUnitsType1 = SafeScanner.getValidatedIntInput(sc, "Enter Number Of Units for Room Type 1: ", 0, 1000);
        int sellPriceType1 = SafeScanner.getValidatedIntInput(sc, "Enter Selling Price for Room Type 1: ", 0, 10000000);

        System.out.print("Enter Room Type 2: ");
        String roomType2 = sc.nextLine().trim();
        if(roomType2.isEmpty()){
            System.out.println("Empty Room Type 2. Please enter a valid input.");
        }

        int noOfUnitsType2 = SafeScanner.getValidatedIntInput(sc, "Enter Number Of Units for Room Type 2: ", 0, 1000);
        int sellPriceType2 = SafeScanner.getValidatedIntInput(sc, "Enter Selling Price for Room Type 2: ", 0, 10000000);
        String appDateOpen = SafeScanner.getValidatedDateInput(sc,"Enter Application Opening Date (DD/MM/YY): ");
        String appDateClose = SafeScanner.getValidatedDateInput(sc,"Enter Application Closing Date (DD/MM/YY): ");

        System.out.print("Enter Manager in Charge: ");
        String manager = sc.nextLine().trim();
        if(manager.isEmpty()){
            System.out.println("Empty Manager Slot. Please enter a valid input.");
        }

        int noOfficersSlots = SafeScanner.getValidatedIntInput(sc, "Enter Number of Officer Slots: ", 0, 10);

        System.out.print("Enter officer names (comma-separated)");
        String[] officers = SafeScanner.getValidatedOfficerNames(sc, noOfficersSlots);

        Project newProject = new Project(projectID,projectName, neighbourhood, roomType1, noOfUnitsType1,
                sellPriceType1, roomType2, noOfUnitsType2, sellPriceType2,
                appDateOpen, appDateClose, manager, noOfficersSlots, officers);

        if (ProjectController.createProject(newProject)) {
            System.out.println("Project created successfully.");
        } else {
            System.out.println("Failed to create project. It might already exist.");
            }
    }

    public void projectChangesMenu() {
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

    public void deleteProject() {
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

    public void updateProjectName() {
        String projectID;
        String newProjectName;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        System.out.println("Please enter the new Project Name:");
        newProjectName = sc.nextLine();
        ProjectController.updateProjectName(projectID, newProjectName);
    }

    public void updateProjectNeighbourhood() {
        String projectID;
        String newNeighbourhood;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        System.out.println("Please enter the New Neighbourhood:");
        newNeighbourhood = sc.nextLine();
        ProjectController.updateProjectNeighbourhood(projectID, newNeighbourhood);
    }

    public void updateProjectRoomType() {
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

    public void updateProjectNumOfUnit() {
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

    public void updateSellingPrice(){
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

    public void updateProjectApplicationOpen(){
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

    public void updateProjectApplicationClose(){
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

    public void updateManagerInCharge(){
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

    public void updateNumberOfOfficers(){
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

