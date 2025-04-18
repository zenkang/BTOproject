package Applicant;


import java.util.*;

import Enumerations.MaritalStatus;
import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;
import Utils.SafeScanner;
import User.UserBoundary;
import Enquiry.EnquiryApplicantBoundary;


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
            System.out.println("5. Enquiry");
            System.out.println("6. ");
            System.out.println("7. Change Password");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 7);

            switch (choice) {
                case 1 -> viewApplicantProfile();
                case 2 -> displayProjectsForApplicant(applicant);
                case 3 -> applyForProject(applicant);
                case 4 -> viewApplication(applicant);
                case 5 -> EnquiryApplicantBoundary.applicantMenu(applicant);
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
            System.out.println("You have a Pending/Successful Application");
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
            //TO BE ADDED: CHECKS TO ENSURE NUM OF UNITS AVAILABLE FOR THE PROJ >1
            String projectID = SafeScanner.getValidatedStringInput(sc,"Enter Project ID you want to apply for: ",ValidprojIDs);
            String roomType;
            if(applicant.getMaritalStatus() == MaritalStatus.SINGLE){
                roomType = "2-room";
            }
            else {
                List<String> validRoomTypes = Arrays.asList("2-room", "3-room");
                roomType = SafeScanner.getValidatedStringInput(sc, "Enter a room type (2-Room or 3-Room): ", validRoomTypes);
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
                    ProjectApplicationController::prettyPrintProjectApplications
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
                ProjectController.prettyPrintApplicantProject(applicant, p);
            }
        }

        // 3) fetch the whole “open” list, then skip anything in appliedIds
        List<Project> filteredProjects = ProjectController.getProjectsForApplicant(applicant);
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects are open to your user group.");
        } else {
            System.out.println("========= Projects =========");
            for (Project proj : filteredProjects) {
                ProjectController.prettyPrintApplicantProject(applicant, proj);
            }
        }
    }



}
