package ProjectApplication;


import Enumerations.ApplicationStatus;
import Manager.Manager;
import Project.Project;
import Project.ProjectController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Utils.RepositoryGetter.*;

/**
 * Controller class for managing project applications.
 * Handles logic for creating, updating, withdrawing, and displaying applications
 * based on roles such as Manager, Officer, and Applicant.
 */
public class ProjectApplicationController {

    /**
     * Retrieves all project applications related to projects managed by the given manager.
     *
     * @param manager the manager whose projects' applications are retrieved
     * @return list of {@code ProjectApplication} objects
     */
    public static ArrayList<ProjectApplication> getAllProjectApplications(Manager manager) {
        List<String> ProjectIDs = ProjectController.getProjectIDsManagedBy(manager.getID());
        ArrayList<ProjectApplication> projectApplications = getProjectApplicationsRepository().getAllProjectApplications();
        // keep only those whose projectID is in ProjectIDs
        projectApplications.removeIf(app ->
                !ProjectIDs.contains(app.getProjectID())
        );
        return projectApplications;
    }

    /**
     * Displays all project applications under the given manager.
     *
     * @param manager the manager whose applications are displayed
     */
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

    /**
     * Updates the status of a project application.
     *
     * @param projectApplication the application to update
     * @param applicationStatus  the new status to set
     * @return true if update was successful
     */
    public static boolean updateApplicationStatus(ProjectApplication projectApplication, ApplicationStatus applicationStatus) {
        projectApplication.setPreviousStatus(applicationStatus);
        projectApplication.setStatus(applicationStatus);
        return getProjectApplicationsRepository().update(projectApplication);
    }

    /**
     * Retrieves all applications submitted by a specific applicant.
     *
     * @param applicantID the ID of the applicant
     * @return list of {@code ProjectApplication} objects
     */
    public static List<ProjectApplication> getApplicationsByApplicantID(String applicantID) {
        return getProjectApplicationsRepository().getByFilter(application -> applicantID.equalsIgnoreCase(application.getApplicantID()));
    }

    /**
     * Creates a new project application for the specified applicant and project.
     *
     * @param projectID    the ID of the project to apply for
     * @param roomType     the room type chosen
     * @param applicantID  the ID of the applicant
     * @return true if application is successfully created
     */
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

    /**
     * Checks whether an applicant has any prior valid applications (excluding unsuccessful).
     *
     * @param applicantID the ID of the applicant
     * @return true if no valid applications exist
     */
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

    /**
     * Gets the number of pending applications for the manager.
     *
     * @param manager the manager
     * @return count of pending applications
     */
    public static int getNumPendingApplications(Manager manager) {
        List<ProjectApplication> list = getApplicationsByStatus(manager, ApplicationStatus.PENDING);
        return list.size();
    }

    /**
     * Gets the application IDs of all pending applications under the manager.
     *
     * @param manager the manager
     * @return list of application IDs
     */
    public static List<String> getPendingApplicationIDs(Manager manager) {
        List<ProjectApplication> list = getApplicationsByStatus(manager, ApplicationStatus.PENDING);
        return list.stream()
                .map(ProjectApplication::getID)
                .toList();
    }

    /**
     * Gets the application IDs of all unsuccessful applications under the manager.
     *
     * @param manager the manager
     * @return list of application IDs
     */
    public static List<String> getUnsuccessfulApplicationIDs(Manager manager) {
        List<ProjectApplication> list = getApplicationsByStatus(manager, ApplicationStatus.UNSUCCESSFUL);
        return list.stream()
                .map(ProjectApplication::getID)
                .toList();
    }

    /**
     * Gets the IDs of successful applications handled by a specific officer.
     *
     * @param officer_id the officer's ID
     * @return list of application IDs
     */
    public static List<String> getSusApplicationIDs(String officer_id) {
        List<ProjectApplication> list = getHandledApplicationsByStatus(officer_id,ApplicationStatus.SUCCESSFUL);
        return list.stream()
                .map(ProjectApplication::getID)
                .toList();
    }

    /**
     * Retrieves applications under a manager filtered by status.
     *
     * @param manager the manager
     * @param status  the desired application status
     * @return list of matching applications
     */
    public static List<ProjectApplication> getApplicationsByStatus(Manager manager, ApplicationStatus status) {
        ArrayList<ProjectApplication> projectApplications = getAllProjectApplications(manager);
        return projectApplications.stream()
                .filter(app -> app.getStatus().equals(status))
                .toList();
    }

    /**
     * Retrieves applications for a specific project ID.
     *
     * @param projectID the ID of the project
     * @return list of matching applications
     */
    public static List<ProjectApplication> getApplicationsByProjectID(String projectID) {
        return getProjectApplicationsRepository().getByFilter(app -> app.getProjectID().equalsIgnoreCase(projectID));
    }

    /**
     * Gets the active application (not UNSUCCESSFUL) of a specific applicant.
     *
     * @param applicantID the applicant's ID
     * @return the current application if found; otherwise, null
     */
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

    /**
     * Retrieves all unsuccessful applications for a given applicant.
     *
     * @param applicantID the applicant's ID
     * @return list of unsuccessful applications
     */
    public static List<ProjectApplication> getUnsuccessApplicationByApplicantID(String applicantID) {
        List<ProjectApplication> list = getProjectApplicationsRepository().getByFilter(
                application -> applicantID.equalsIgnoreCase(application.getApplicantID()) &&
                        application.getStatus() == ApplicationStatus.UNSUCCESSFUL
        );
        return list;
    }

    /**
     * Gets the number of successful applications handled by a specific officer.
     *
     * @param id the officer's ID
     * @return count of successful applications
     */
    public static int getNumSusApplications(String id) {
        List<Project> handledProjects = getProjectRepository().getByFilter(p -> p.getOfficer().contains(id));
        List<String> handledProjectIds = handledProjects.stream()
                .map(Project::getID)
                .toList();
        return (int) getProjectApplicationsRepository().getAll().stream()
                .filter(app -> handledProjectIds.contains(app.getProjectID()))
                .filter(app -> app.getStatus().equals(ApplicationStatus.SUCCESSFUL))
                .count();
    }

    /**
     * Displays all applications handled by the officer.
     *
     * @param id the officer's ID
     */
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

    /**
     * Retrieves applications handled by an officer filtered by a specific status.
     *
     * @param id                the officer's ID
     * @param applicationStatus the status to filter by
     * @return list of matching applications
     */
    public static List<ProjectApplication> getHandledApplicationsByStatus(String id, ApplicationStatus applicationStatus) {
        List<Project> handledProjects = getProjectRepository().getByFilter(p -> p.getOfficer().contains(id));
        List<String> handledProjectIds = handledProjects.stream()
                .map(Project::getID)
                .toList();
        return  getProjectApplicationsRepository().getAll().stream()
                .filter(app -> handledProjectIds.contains(app.getProjectID()))
                .filter(app -> app.getStatus().equals(applicationStatus)).toList();
    }

    /**
     * Withdraws the active application of an applicant.
     * If the status is BOOKED, reverts the unit count.
     *
     * @param applicantID the ID of the applicant
     * @return true if successful
     */
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

    /**
     * Gets the number of rejected applications under the manager.
     *
     * @param manager the manager
     * @return count of rejected applications
     */
    public static int getNumRejectedApplications(Manager manager) {
        List<ProjectApplication> list = getApplicationsByStatus(manager, ApplicationStatus.UNSUCCESSFUL);
        return list.size();
    }

    /**
     * Reverts the withdrawal of an application by restoring its previous status.
     * Adjusts unit counts if necessary.
     *
     * @param applicationID the ID of the application
     * @return true if successful
     */
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
