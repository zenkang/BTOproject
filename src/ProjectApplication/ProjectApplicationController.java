package ProjectApplication;

import Applicant.Applicant;
import Enumerations.MaritalStatus;
import Enumerations.ProjectApplicationStatus;
import Enumerations.ApplicationStatus;

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

    public static void displayProjectApplicationMenu(Applicant applicant){
        ProjectApplicationBoundary projectApplicationBoundary = new ProjectApplicationBoundary();
        projectApplicationBoundary.applyForProject(applicant);
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

    public static ProjectApplicationStatus createProjectApplication(ProjectApplication newApplication, Applicant applicant) {
        ProjectApplicationRepository applicationRepository = retrieveProjectApplicationsRepository();
        if (applicant.getMaritalStatus() == MaritalStatus.SINGLE) {
            if (applicant.getAge() < 35) {
                return ProjectApplicationStatus.AGE_RESTRICTION_SINGLE;
            }
            if (!newApplication.getRoomType().equalsIgnoreCase("2-Room")) {
                return ProjectApplicationStatus.FLAT_TYPE_SINGLE;
            }
        } else if (applicant.getMaritalStatus() == MaritalStatus.MARRIED) {
            if (applicant.getAge() < 21) {
                return ProjectApplicationStatus.AGE_RESTRICTION_MARRIED;
            }
        } else {
            return ProjectApplicationStatus.ELIGIBILITY_UNDETERMINED;
        }

        boolean created = applicationRepository.create(newApplication);
        if (created) {
            return ProjectApplicationStatus.SUCCESS;
        } else {
            return ProjectApplicationStatus.FAILURE;
        }
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

    public static boolean checkPreviousApplication(String applicantID){
        ProjectApplicationRepository applicationRepository = retrieveProjectApplicationsRepository();
        for (ProjectApplication existing : applicationRepository.getAllProjectApplications()) {
            if (existing.getApplicantID().equalsIgnoreCase(applicantID)) {
                return false;
            }
        }
        return true;
    }
}
