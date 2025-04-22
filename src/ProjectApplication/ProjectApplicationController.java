package ProjectApplication;


import Enumerations.ApplicationStatus;
import Manager.Manager;
import Project.Project;
import Project.ProjectController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Utils.RepositoryGetter.*;

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
        projectApplication.setPreviousStatus(applicationStatus);
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
    public static List<ProjectApplication> getApplicationsByApplicantID(String applicantID) {
        return getProjectApplicationsRepository().getByFilter(application -> applicantID.equalsIgnoreCase(application.getApplicantID()));
    }

    public static boolean createProjectApplication(String projectID,String roomType, String applicantID) {
        String appID = getProjectApplicationsRepository().generateId("APP");
        ProjectApplication application = new ProjectApplication(
                appID,
                projectID,
                roomType,
                applicantID,
                ApplicationStatus.PENDING,
                ApplicationStatus.PENDING,
                null);
        return getProjectApplicationsRepository().create(application);
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
    public static List<String> getUnsuccessfulApplicationIDs(Manager manager) {
        List<ProjectApplication> list = getApplicationsByStatus(manager, ApplicationStatus.UNSUCCESSFUL);
        return list.stream()
                .map(ProjectApplication::getID)
                .toList();
    }
    public static List<String> getSusApplicationIDs(String officer_id) {
        List<ProjectApplication> list = getHandledApplicationsByStatus(officer_id,ApplicationStatus.SUCCESSFUL);
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

    public static ProjectApplication getCurrentApplicationByApplicantID(String applicantID) {
        List<ProjectApplication> list = getProjectApplicationsRepository().getByFilter(
                application -> applicantID.equalsIgnoreCase(application.getApplicantID()) &&
                        application.getStatus() != ApplicationStatus.UNSUCCESSFUL
        );

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0); // returns the first active application (e.g., PENDING or BOOKED)
    }


    public static int getNumSusApplications(String id) {
        List<Project> handledProjects = getProjectRepository().getByFilter(p -> Arrays.asList(p.getOfficer()).contains(id));
        List<String> handledProjectIds = handledProjects.stream()
                .map(Project::getID)
                .toList();
        return (int) getProjectApplicationsRepository().getAll().stream()
                .filter(app -> handledProjectIds.contains(app.getProjectID()))
                .filter(app -> app.getStatus().equals(ApplicationStatus.SUCCESSFUL))
                .count();
    }

    public static void displayAllHandledProjectApplications(String id) {
        List<Project> handledProjects = getProjectRepository().getByFilter(p -> Arrays.asList(p.getOfficer()).contains(id));
        List<String> handledProjectIds = handledProjects.stream()
                .map(Project::getID)
                .toList();
        List<ProjectApplication> projectApplications = getProjectApplicationsRepository().getAll().stream()
                .filter(app -> handledProjectIds.contains(app.getProjectID())).toList();
        if(projectApplications.isEmpty()){
            System.out.println("No Applications Available.");
        }
        else{
            for (ProjectApplication projectApplication : projectApplications) {
                System.out.println(projectApplication);
            }
        }

    }

    public static List<ProjectApplication> getHandledApplicationsByStatus(String id, ApplicationStatus applicationStatus) {
        List<Project> handledProjects = getProjectRepository().getByFilter(p -> Arrays.asList(p.getOfficer()).contains(id));
        List<String> handledProjectIds = handledProjects.stream()
                .map(Project::getID)
                .toList();
        return  getProjectApplicationsRepository().getAll().stream()
                .filter(app -> handledProjectIds.contains(app.getProjectID()))
                .filter(app -> app.getStatus().equals(applicationStatus)).toList();
    }
    public static boolean withdrawApplication(String applicantID) {
        ProjectApplication application = getCurrentApplicationByApplicantID(applicantID);

        if (application == null) {
            return false;
        }
        Project project = ProjectController.getProjectByID(application.getProjectID());
        if(application.getStatus().equals(ApplicationStatus.BOOKED)){
            int newUnit;
            String roomtype = application.getRoomType();
            if(project.getType1().equalsIgnoreCase(roomtype)){
                newUnit = project.getNoOfUnitsType1() + 1;
                ProjectController.updateProjectNumOfRoomType1(project.getID(),newUnit);
            }
            else{
                newUnit = project.getNoOfUnitsType2() + 1;
                ProjectController.updateProjectNumOfRoomType2(project.getID(),newUnit);
            }
        }
        application.setStatus(ApplicationStatus.UNSUCCESSFUL);


        return getProjectApplicationsRepository().update(application);
    }


    public static int getNumRejectedApplications(Manager manager) {
        List<ProjectApplication> list = getApplicationsByStatus(manager, ApplicationStatus.UNSUCCESSFUL);
        return list.size();
    }

    public static boolean rejectWithdrawal(String applicationID) {
        ProjectApplication application = getProjectApplicationsRepository().getByID(applicationID);
        Project project = ProjectController.getProjectByID(application.getProjectID());
        int newUnit;
        String roomtype = application.getRoomType();
        if(application.getPreviousStatus().equals(ApplicationStatus.BOOKED)){
            if(project.getType1().equalsIgnoreCase(roomtype)){
                newUnit = project.getNoOfUnitsType1() - 1;
                ProjectController.updateProjectNumOfRoomType1(project.getID(),newUnit);
            }
            else{
                newUnit = project.getNoOfUnitsType2() - 1;
                ProjectController.updateProjectNumOfRoomType2(project.getID(),newUnit);
            }
        }
        application.setStatus(application.getPreviousStatus());
        return getProjectApplicationsRepository().update(application);
    }
}
