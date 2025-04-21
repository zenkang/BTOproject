package ProjectApplication;


import Applicant.Applicant;
import Enumerations.ApplicationStatus;

import Manager.Manager;
import Project.Project;
import Project.ProjectController;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public static void generateReceipt(String name, ProjectApplication application) {
        Project project = getProjectRepository().getByID(application.getProjectID());
        Applicant applicant = getApplicantRepository().getByID(application.getApplicantID());
        if (project == null || applicant == null) {
            System.out.println("Error: Missing project or applicant data");
            return;
        }
        String receiptContent = String.format(
                "=== HDB FLAT BOOKING RECEIPT ===\n" +
                        "Date: %s\n\n" +
                        "Applicant Details:\n" +
                        "Name: %s\n" +
                        "NRIC: %s\n" +
                        "Age: %d\n" +
                        "Marital Status: %s\n\n" +
                        "Project Details:\n" +
                        "Project ID: %s\n" +
                        "Name: %s\n" +
                        "Neighborhood: %s\n" +
                        "Flat Type: %s\n\n" +
                        "Officer Details:\n" +
                        "Name: %s\n" +
                        "NRIC: %s\n" +
                        "Generated on: %s\n" +
                        "================================",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                applicant.getName(),
                applicant.getNric(),
                applicant.getAge(),
                applicant.getMaritalStatus(),
                project.getID(),
                project.getProjectName(),
                project.getNeighbourhood(),
                application.getRoomType(),
                name,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        );

        String fileName = String.format("receipt_%s_%s.txt",
                applicant.getNric(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(receiptContent);
            System.out.println("Receipt generated: " + fileName);
        } catch (IOException e) {
            System.out.println("Error generating receipt: " + e.getMessage());
        }

    }
}
