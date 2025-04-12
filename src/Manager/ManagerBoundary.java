package Manager;

import Applicant.ApplicantController;
import Enquiry.EnquiryBoundary;
import Project.ProjectBoundary;
import Project.ProjectController;
import User.UserBoundary;
import Utils.SafeScanner;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ManagerBoundary  {
    private Manager manager;
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
            System.out.println("3. Apply Projects");
            System.out.println("4. Display Projects Created By Me");
            System.out.println("5. Enquiry");
            System.out.println("6. ");
            System.out.println("7. Change Password");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 7);

            switch (choice) {
                case 1 -> viewApplicantProfile();
                case 2 -> ProjectBoundary.displayProjectMenu();
                case 3, 4 -> ProjectController.displayProjectsCreatedByManager(manager.getName());
                case 5 -> System.out.println("TBC");
                case 6 -> System.out.println("TBC");
                case 7 -> changePassword();
                case 0 -> System.out.println("Exiting the Applicant Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0) ;
        sc.close();
    }
    public void viewApplicantProfile() {
        Scanner sc = new Scanner(System.in);
        String selection;
        do {
            System.out.println("\n=== Applicant Profile ===");
            System.out.println("Name: " + manager.getName());
            System.out.println("NRIC: " + manager.getNric());
            System.out.println("Age: " + manager.getAge());
            System.out.println("MaritalStatus: " + manager.getMaritalStatus().toString());
            System.out.println("Password: " + manager.getPassword());

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
            System.out.println("1. Update Name: " + manager.getName());
            System.out.println("2. Update Age: " + manager.getAge());
            System.out.println("3. Update Marital Status: " + manager.getMaritalStatus().toString());
            System.out.println("0. Back");

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


}
