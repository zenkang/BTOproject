package ProjectFilter;

import Applicant.Applicant;
import Project.Project;
import Utils.SafeScanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ProjectFilterBoundary {
    private static ProjectFilter currentFilterCriteria = new ProjectFilter();
    static Scanner sc = new Scanner(System.in);
    public static void displayFilteredProjects() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Project> filteredProjects = ProjectFilterController.getFilteredProjects(
                currentFilterCriteria.getNeighbourhoodName(), currentFilterCriteria.getFlatType()
        );

        if (filteredProjects.isEmpty()) {
            System.out.println("No projects match your filter criteria.");
        } else {
            System.out.println("Filtered Projects:");
            for (Project project : filteredProjects) {
                System.out.println("Project ID: " + project.getID());
                System.out.println("Project name: " + project.getProjectName());
                System.out.println("Neighbourhood: " + project.getNeighbourhood());
                System.out.println("Room Type 1: " + project.getType1());
                System.out.println("Number of units for Room Type 1: " + project.getNoOfUnitsType1());
                System.out.println("Selling price of Room Type 1: " + project.getSellPriceType1());
                System.out.println("Room Type 2: " + project.getType2());
                System.out.println("Number of units for Room Type 2: " + project.getNoOfUnitsType2());
                System.out.println("Selling price of Room Type 2: " + project.getSellPriceType2());
                System.out.println("Application Open Date: " + dateFormatter.format(project.getAppDateOpen()));
                System.out.println("Application Close Date: " + dateFormatter.format(project.getAppDateClose()));
                System.out.println("Manager-in-charge: " + project.getManager());
                System.out.println("Number of Officer Slot(s): " + project.getNoOfficersSlots());
                System.out.println("Officer(s) Assigned: ");
                String[] officers = project.getOfficer();
                for (String officer : officers) {
                    System.out.println(officer);
                }
                System.out.println("Active Project: " + project.isVisibility());
                System.out.println("------------------------");
            }
            System.out.println("Filters Applied: " +
                    "Location: " + currentFilterCriteria.getNeighbourhoodName() +
                    " Room Type: " + currentFilterCriteria.getFlatType());
        }
    }

    public static void displayFilterMenu() {
        int choice;
        do {
            System.out.println("\n=== Project Filter Menu ===");
            System.out.println("1. Location");
            System.out.println("2. Flat Type");
            System.out.println("3. Reset Filters");
            System.out.println("0. Exit");
            System.out.println("Filters Applied: "+
                    "Location: "+currentFilterCriteria.getNeighbourhoodName()+
                    " Room Type:"+currentFilterCriteria.getFlatType());
            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 3);

            switch (choice) {
                case 1 ->{
                    List<String> validNeighbourhoodOptions = Arrays.asList("bedok", "punggol");
                    String neighbourhood = SafeScanner.getValidatedStringInput(sc,"Enter location filter:", validNeighbourhoodOptions);
                    currentFilterCriteria.setNeighbourhoodName(neighbourhood);
                }
                case 2 ->{
                    List<String> validRoomOptions = Arrays.asList("2-room","3-room");
                    String flatType = SafeScanner.getValidatedStringInput(sc,"Enter flat type filter(e.g.,2-Room,3-Room:)",validRoomOptions);
                    currentFilterCriteria.setFlatType(flatType);
                }
                case 3 ->{
                    currentFilterCriteria.setNeighbourhoodName("");
                    currentFilterCriteria.setFlatType("");
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

    public void displayProjectsForApplicant(Applicant applicant) {
        /*List<Project> filteredProjects = ProjectFilterController.getProjectsForApplicant(applicant);
        if(filteredProjects.isEmpty()){
            System.out.println("No projects are open to your user group.");
        } else {
            filteredProjects.forEach(System.out::println);
        }*/
        System.out.println("display projects for applicant " + applicant.getName());
    }

    public static void displayProjectsCreatedByManager(String managerName) {
        List<Project> managerProjects = ProjectFilterController.getProjectsCreatedByManager(managerName);
        if (managerProjects.isEmpty()) {
            System.out.println("You have not created any projects.");
        } else {
            System.out.println("Projects created by you:");
            managerProjects.forEach(System.out::println);
        }
    }
}
