package ProjectApplication;

import Applicant.Applicant;
import Enumerations.MaritalStatus;
import Enumerations.ProjectApplicationStatus;
import java.util.ArrayList;

import static Utils.RepositoryGetter.getProjectApplicationsRepository;


public class ProjectApplicationController {

    public static ArrayList<ProjectApplication> getAllProjectApplications() {
        return getProjectApplicationsRepository().getAllProjectApplications();
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
        ProjectApplicationRepository repository = getProjectApplicationsRepository();
        for (ProjectApplication app : repository.getAllProjectApplications()) {
            if (app.getApplicantID().equalsIgnoreCase(applicantID)) {
                return app;
            }
        }
        return null;
    }

    public static ProjectApplicationStatus createProjectApplication(ProjectApplication newApplication, Applicant applicant) {
        ProjectApplicationRepository applicationRepository = getProjectApplicationsRepository();
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
        ProjectApplicationRepository projectApplicationRepository = getProjectApplicationsRepository();
        return projectApplicationRepository.deleteProjectApplicationByID(appID);
    }


    public static boolean checkPreviousApplication(String applicantID){
        ProjectApplicationRepository applicationRepository = getProjectApplicationsRepository();
        for (ProjectApplication existing : applicationRepository.getAllProjectApplications()) {
            if (existing.getApplicantID().equalsIgnoreCase(applicantID)) {
                return false;
            }
        }
        return true;
    }

}
