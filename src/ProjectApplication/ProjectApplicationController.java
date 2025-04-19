package ProjectApplication;

import Applicant.Applicant;
import Enumerations.ApplicationStatus;
import Enumerations.MaritalStatus;
import Enumerations.ProjectApplicationStatus;
import Manager.Manager;
import Project.Project;
import Project.ProjectController;

import java.util.ArrayList;
import java.util.List;

import static Utils.RepositoryGetter.getProjectApplicationsRepository;


public class ProjectApplicationController {

    public static ArrayList<ProjectApplication> getAllProjectApplications(Manager manager) {
        List<String> ProjectIDs = ProjectController.getProjectIDsManagedBy(manager.getID());
        ArrayList<ProjectApplication> projectApplications = getProjectApplicationsRepository().getAllProjectApplications();
        // keep only those whose projectID is in ProjectIDs
        projectApplications.removeIf(app ->
                !ProjectIDs.contains(app.getProjectID())
        );
        return projectApplications;
    }

    public static void displayAllProjectApplications(Manager manager) {
        ArrayList<ProjectApplication> projectApplications = getAllProjectApplications(manager);
        if (projectApplications.isEmpty()) {
            System.out.println("No Applications Available.");
        } else {
            for (ProjectApplication projectApplication : projectApplications) {
                System.out.println(projectApplication);
            }
        }
    }

    public static boolean updateApplicationStatus(ProjectApplication projectApplication, ApplicationStatus applicationStatus) {
        if(projectApplication.getStatus() == ApplicationStatus.PENDING && applicationStatus == ApplicationStatus.SUCCESSFUL) {
            String roomType = projectApplication.getRoomType();
            String projectID = projectApplication.getProjectID();
            int unit;
            if(ProjectController.getProjectByID(projectID).getType1().equalsIgnoreCase(roomType)){
                unit = ProjectController.getProjectByID(projectID).getNoOfUnitsType1();
                unit = unit - 1;
                ProjectController.updateProjectNumOfRoomType1(projectID, unit);
            }
            else if(ProjectController.getProjectByID(projectID).getType2().equalsIgnoreCase(roomType)){
                unit = ProjectController.getProjectByID(projectID).getNoOfUnitsType2();
                unit = unit - 1;
                ProjectController.updateProjectNumOfRoomType2(projectID, unit);
            }
        }
        projectApplication.setStatus(applicationStatus);
        return getProjectApplicationsRepository().update(projectApplication);
    }


    public static List<ProjectApplication> getApplicationByApplicantID(String applicantID) {
        return getProjectApplicationsRepository().getByFilter(application -> applicantID.equalsIgnoreCase(application.getApplicantID()));
    }

    public static boolean createProjectApplication(String projectID,String roomType, String applicantID) {
        String appID = getProjectApplicationsRepository().generateId("APP");
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
                if (existing.getStatus() == ApplicationStatus.UNSUCCESSFUL) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    public static int getNumPendingApplications(Manager manager) {
        List<ProjectApplication> list = getApplicationsByStatus(manager, ApplicationStatus.PENDING);
        return list.size();
    }

    public static List<String> getPendingApplicationIDs(Manager manager) {
        List<ProjectApplication> list = getApplicationsByStatus(manager, ApplicationStatus.PENDING);
        return list.stream()
                .map(ProjectApplication::getID)
                .toList();
    }

    public static List<ProjectApplication> getApplicationsByStatus(Manager manager, ApplicationStatus status) {
        ArrayList<ProjectApplication> projectApplications = getAllProjectApplications(manager);
        return projectApplications.stream()
                .filter(app -> app.getStatus().equals(status))
                .toList();
    }

    public static List<ProjectApplication> getApplicationsByProjectID(String projectID) {
        return getProjectApplicationsRepository().getByFilter(app -> app.getProjectID().equalsIgnoreCase(projectID));
    }

    public static List<ProjectApplication> getApplicationsByRoomType(String roomType) {
        return getProjectApplicationsRepository().getByFilter(app -> app.getRoomType().equalsIgnoreCase(roomType));
    }



}
