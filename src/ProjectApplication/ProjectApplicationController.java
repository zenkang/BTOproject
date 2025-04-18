package ProjectApplication;

import Applicant.Applicant;
import Enumerations.ApplicationStatus;
import Enumerations.MaritalStatus;
import Enumerations.ProjectApplicationStatus;
import Project.Project;
import Project.ProjectController;

import java.util.ArrayList;
import java.util.List;

import static Utils.RepositoryGetter.getProjectApplicationsRepository;


public class ProjectApplicationController {

    public static ArrayList<ProjectApplication> getAllProjectApplications() {
        return getProjectApplicationsRepository().getAllProjectApplications();
    }
    public static void displayAllProjectApplications(){
        ArrayList<ProjectApplication> projectApplications = getAllProjectApplications();
        if (projectApplications.isEmpty()) {
            System.out.println("No Applications Available.");
        } else {
            for (ProjectApplication projectApplication : projectApplications) {
                System.out.println(projectApplication);
            }
        }
    }

    public static boolean updateApplicationStatus(ProjectApplication projectApplication, ApplicationStatus applicationStatus) {
        projectApplication.setStatus(applicationStatus);
        return getProjectApplicationsRepository().update(projectApplication);
    }


    public static ProjectApplication getApplicationByApplicantID(String applicantID) {
        for (ProjectApplication app : getProjectApplicationsRepository().getAllProjectApplications()) {
            if (app.getApplicantID().equalsIgnoreCase(applicantID)) {
                return app;
            }
        }
        return null;
    }

    public static boolean createProjectApplication(String projectID,String roomType, String applicantID) {
        String appID = getProjectApplicationsRepository().generateId();
        ProjectApplication application = new ProjectApplication(
                appID,
                projectID,
                roomType,
                applicantID,
                ApplicationStatus.PENDING
        );
        return getProjectApplicationsRepository().create(application);
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

    public static int getNumPendingApplications() {
        List<ProjectApplication> list = getProjectApplicationsRepository().getByFilter(application -> application.getStatus().equals(ApplicationStatus.PENDING));
        return list.size();
    }

    public static List<ProjectApplication> getApplicationsByStatus(ApplicationStatus status) {
        return getProjectApplicationsRepository().getByFilter(app -> app.getStatus().equals(status));
    }

    public static List<ProjectApplication> getApplicationsByProjectID(String projectID) {
        return getProjectApplicationsRepository().getByFilter(app -> app.getProjectID().equalsIgnoreCase(projectID));
    }

    public static List<ProjectApplication> getApplicationsByRoomType(String roomType) {
        return getProjectApplicationsRepository().getByFilter(app -> app.getRoomType().equalsIgnoreCase(roomType));
    }







}
