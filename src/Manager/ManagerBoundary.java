package Manager;


import Applicant.ApplicantController;
import Enumerations.ApplicationStatus;
import Project.ProjectBoundary;
import Applicant.Applicant;

import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;
import User.UserBoundary;
import Utils.SafeScanner;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static Utils.RepositoryGetter.*;

public class ManagerBoundary  {
    private Manager manager;
    private ProjectBoundary projectBoundary;
    public ManagerBoundary(Manager manager) {
        this.projectBoundary = new ProjectBoundary(manager);
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
                case 2 -> ProjectBoundary.displayProjectMenu();
                case 3 -> viewApplicantApplications();
                case 4 -> System.out.println("TBC");
                case 5 -> System.out.println("TBC");
                case 6 -> changePassword();
                case 0 -> System.out.println("Exiting the Manager Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0 && choice != 7) ;
        if(choice == 0){
            sc.close();
        }
    }
    private void viewProfile() {
        Scanner sc = new Scanner(System.in);
        String selection;
        do {
            Utils.PrettyPrint.prettyPrint(manager);

            List<String> validOptions = Arrays.asList("y", "n");
            selection = SafeScanner.getValidatedStringInput(sc,"Would you like to update your profile?\nEnter: y/n\n",validOptions);
            if (selection.equals("y")) {
                updateProfile();
            }
        }while(!selection.equals("n"));

    }

    private void updateProfile() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do{
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

    private void updateAge(){
        Scanner sc = new Scanner(System.in);
        int age;
        age = SafeScanner.getValidatedIntInput(sc, "Enter your new age: ", 0, 200);
        if(ManagerController.updateAge(manager, age)){
            System.out.println("Your age has been updated!\n");
        }
        else{
            System.out.println("Update failed, try again later\n");
        }
    }

    private void updateName(){
        Scanner sc = new Scanner(System.in);
        String newName = SafeScanner.getValidatedStringInput(sc,"Enter your new Name: ",50);
        if(ManagerController.updateName(manager, newName)){
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
        if(ManagerController.updateMaritalStatus(manager, maritalStatus)){
            System.out.println("Your marital status has been updated!\n");
        }
        else{
            System.out.println("Update failed, try again later\n");
        }
    }


    //project applications ***TO BE COMPLETED!!!
    private void viewApplicantApplications() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            int numPending = ProjectApplicationController.getNumPendingApplications();
            System.out.println("\n=== Applications ===");
            System.out.println("1. View all Applications");
            System.out.println("2. View pending Applications " + ((numPending==0)? "" : "("+numPending+")" ));
            System.out.println("3. View Filtered applications");
            System.out.println("4. Update Filters");
            System.out.println("0. Back");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> ProjectApplicationController.displayAllProjectApplications();
                case 2 -> {
                            List<ProjectApplication> list = ProjectApplicationController.getApplicationsByStatus(ApplicationStatus.PENDING);
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
            if(choice != 0 && !getProjectApplicationsRepository().isEmpty()) {
                List<String> validOptions = Arrays.asList("y", "n");
                String selection = SafeScanner.getValidatedStringInput(sc,"Update application status?\nEnter: y/n\n",validOptions);
                if (selection.equals("y")) {
                    String applicationID = SafeScanner.getValidApplicationID(sc,"Enter the application ID: ");
                    updateApplicationStatus(applicationID);
                }
            }
        }
        while (choice != 0) ;
    }

    private void updateApplicationStatus(String applicationID) {
        Scanner sc = new Scanner(System.in);
        ProjectApplication application = getProjectApplicationsRepository().getByID(applicationID);
        Applicant applicant = ApplicantController.getApplicantById(application.getApplicantID());
        Utils.PrettyPrint.prettyPrint(applicant);

        List<String> validOptions = Arrays.asList("p", "s", "u", "b","P","S","U","B");
        String selection = SafeScanner.getValidatedStringInput(sc,"\nUpdated Status: (p : Pending, s : Successful, u : Unsuccessful, b : Booked) \n",validOptions);
        ApplicationStatus status = null;
        switch (selection.toLowerCase()) {
            case "p" -> status = ApplicationStatus.PENDING;
            case "s" -> status = ApplicationStatus.SUCCESSFUL;
            case "u" -> status = ApplicationStatus.UNSUCCESSFUL;
            case "b" -> status = ApplicationStatus.BOOKED;
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
        if(ProjectApplicationController.updateApplicationStatus(application, status)){
            System.out.println("Application status updated!");
        }
        else{
            System.out.println("Update failed, try again later\n");
        }
    }



}
