package Project;

import Enquiry.EnquiryBoundary;
import Project.Project;
import Project.ProjectBoundary;
import Utils.SafeScanner;

import java.util.ArrayList;
import java.util.Scanner;



public class ProjectBoundary {
    Scanner sc = new Scanner(System.in);
    public void displayProjectMenu(){
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
                case 3 ->  projectChangesMenu();
                case 4 ->  deleteProject();
                case 0 -> System.out.println("Exiting the Project Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0) ;
        sc.close();
    }
    public void displayProjects(){
        ArrayList<Project> projects = ProjectController.getAllProjects();
        if(projects.isEmpty()){
            System.out.println("No Projects Available.");
        }
        else {
            for (Project project : projects) {
                System.out.println(project);
            }
        }
    }
    public void createNewProject(){
        System.out.println("\n=== Project Creator ===");
    }
    public void projectChangesMenu(){
        int choice;
        do {
            System.out.println("\n=== Project Menu ===");
            System.out.println("1. Update Project Name");
            System.out.println("2. Update Neighbourhood");
            System.out.println("3. Update Type of Flat");
            System.out.println("4. Update Number of Units");
            System.out.println("5. Update Application Opening Date");
            System.out.println("6. Update Application Closing Data");
            System.out.println("7. Update Manager in charge");
            System.out.println("8. Update Maximum HDB Officer Slots");
            System.out.println("9. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 9);

            switch (choice) {
                case 1 -> updateProjectName();
                case 2 -> {}
                case 3 -> {
                }
                case 4 ->{
                    System.out.println("Enter the project name:");
                    String projectName = sc.nextLine();
                    System.out.println("Please enter the new room type:");
                    String newRoomType = sc.nextLine();
                    ProjectController.updateProjectRoomType1(projectName,newRoomType);
                }

                case 0 -> System.out.println("Exiting the Applicant Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0) ;
        sc.close();
    }
    public void deleteProject(){
        System.out.println("\n=== Deleting Project ===");
    }

    public void updateProjectName(){
        String projectID;
        String newProjectName;
        System.out.println("Please enter the Project ID:");
        projectID = sc.nextLine();
        System.out.println("Please enter the new project name:");
        newProjectName= sc.nextLine();
        ProjectController.updateProjectName(projectID,newProjectName);
    }
}

