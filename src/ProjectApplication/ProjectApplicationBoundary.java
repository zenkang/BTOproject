package ProjectApplication;

import Applicant.Applicant;
import Enumerations.ApplicationStatus;
import Enumerations.ProjectApplicationStatus;

import Utils.SafeScanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ProjectApplicationBoundary {
    public void displayProjectApplications() {
        ArrayList<ProjectApplication> projectApplications = ProjectApplicationController.getAllProjectApplications();
        if (projectApplications.isEmpty()) {
            System.out.println("No Projects Available.");
        } else {
            for (ProjectApplication projectApplication : projectApplications) {
                System.out.println(projectApplication);
            }
        }

    }
    public void displayUserProjectApplication(String applicantID) {
        ProjectApplication currentApp = ProjectApplicationController.getApplicationByApplicantID(applicantID);
        if (currentApp == null) {
            System.out.println("You have not applied for any Project.");
        } else {
            System.out.println("Your current Project Application:");
            System.out.println(currentApp);
        }
    }
    public void applyForProject(Applicant applicant) {
        Scanner sc = new Scanner(System.in);
        if (!ProjectApplicationController.checkPreviousApplication(applicant.getNric())) {
            System.out.println("You have already submitted an Application Before.");
        } else {
            System.out.println("\n=== Project Application ===");
            String appID = ProjectApplicationController.generateNewAppID();
            System.out.println("Your Application ID is: "+appID);
            System.out.print("Enter Project ID you want to apply for: ");
            String projectID = SafeScanner.getValidProjectID(sc);
            List<String> validRoomTypes = Arrays.asList("2-room", "3-room");
            String roomType = SafeScanner.getValidatedStringInput(sc, "Enter a room type (2-Room or 3-Room): ", validRoomTypes);

            ProjectApplication application = new ProjectApplication(
                    appID,
                    projectID,
                    roomType,
                    applicant.getID(),
                    ApplicationStatus.PENDING
            );

            ProjectApplicationStatus status = ProjectApplicationController.createProjectApplication(application, applicant);
            switch(status) {
                case SUCCESS-> System.out.println("Project application created successfully.");
                case AGE_RESTRICTION_SINGLE-> System.out.println("Singles must be 35 years or older to apply for a project.");
                case FLAT_TYPE_SINGLE-> System.out.println("Singles can only apply for a 2-Room flat.");
                case AGE_RESTRICTION_MARRIED-> System.out.println("Married applicants must be 21 years or older to apply.");
                case ELIGIBILITY_UNDETERMINED -> System.out.println("Applicant eligibility cannot be determined.");
                default-> System.out.println("Failed to create project application.");
            }

        }
    }
}
