package ProjectApplication;

import Applicant.Applicant;
import Enumerations.MaritalStatus;


import java.util.ArrayList;

public class ProjectApplicationController {
    private static ProjectApplicationRepository retrieveProjectApplicationsRepository() {
        return new ProjectApplicationRepository("./src/data/ProjectApplication.csv");
    }
    public static ArrayList<ProjectApplication> getAllProjectApplications() {
        return retrieveProjectApplicationsRepository().getAllProjectApplications();
    }
    public static void displayAllProjectApplications(){
        ProjectApplicationBoundary projectApplicationBoundary = new ProjectApplicationBoundary();
        projectApplicationBoundary.displayProjectApplications();
    }

    public static void displayUserProjectApplication(String applicantID){
        ProjectApplicationBoundary projectApplicationBoundary = new ProjectApplicationBoundary();
        projectApplicationBoundary.displayUserProjectApplication(applicantID);
    }

    public static ProjectApplication getApplicationByApplicantID(String applicantID) {
        ProjectApplicationRepository repository = retrieveProjectApplicationsRepository();
        for (ProjectApplication app : repository.getAllProjectApplications()) {
            if (app.getApplicantID().equalsIgnoreCase(applicantID)) {
                return app;
            }
        }
        return null;
    }

    public static boolean createProjectApplication(ProjectApplication newApplication, Applicant applicant) {
        ProjectApplicationRepository applicationRepository = retrieveProjectApplicationsRepository();
        for (ProjectApplication existing : applicationRepository.getAllProjectApplications()) {
            if (existing.getApplicantID().equalsIgnoreCase(applicant.getID())) {
                System.out.println("You have already applied for a project and cannot apply for multiple projects.");
                return false;
            }
        }

        if (applicant.getMaritalStatus() == MaritalStatus.SINGLE) {
            if (applicant.getAge() < 35) {
                System.out.println("Singles must be 35 years or older to apply for a project.");
                return false;
            }
            if (!newApplication.getRoomType().equalsIgnoreCase("2-Room")) {
                System.out.println("Singles can only apply for a 2-Room flat.");
                return false;
            }
        } else if (applicant.getMaritalStatus() == MaritalStatus.MARRIED) {
            if (applicant.getAge() < 21) {
                System.out.println("Married applicants must be 21 years or older to apply.");
                return false;
            }
        } else {
            System.out.println("Applicant eligibility cannot be determined.");
            return false;
        }

        boolean created = applicationRepository.create(newApplication);
        if (created) {
            System.out.println("Project application created successfully.");
        } else {
            System.out.println("Failed to create project application.");
        }
        return created;
    }

    public static boolean deleteProjectApplication(String appID) {
        ProjectApplicationRepository projectApplicationRepository = retrieveProjectApplicationsRepository();
        return projectApplicationRepository.deleteProjectApplicationByID(appID);
    }

        public static String generateNewAppID() {
            ProjectApplicationRepository repository = retrieveProjectApplicationsRepository();
            int maxID = 0;
            for (ProjectApplication app : repository.getAllProjectApplications()) {
                try {
                    int idNum = Integer.parseInt(app.getID());
                    if (idNum > maxID) {
                        maxID = idNum;
                    }
                } catch (NumberFormatException e) {
                }
            }
            return String.format("%03d", maxID + 1);
    }
}
