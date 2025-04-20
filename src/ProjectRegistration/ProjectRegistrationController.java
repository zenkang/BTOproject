package ProjectRegistration;

import Enumerations.RegistrationStatus;

import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;


import java.util.List;


import static Utils.RepositoryGetter.getProjectApplicationsRepository;
import static Utils.RepositoryGetter.getProjectRegistrationRepository;

public class ProjectRegistrationController {
    public static boolean canRegisterForProject(String officer_id, String project_id) {
        List<ProjectApplication> projectApplications = ProjectApplicationController.getApplicationByApplicantID(officer_id);
        projectApplications = projectApplications.stream()
                .filter(p -> p.getProjectID().equalsIgnoreCase(project_id)).toList();

        Project newProject = ProjectController.getProjectByID(project_id);
        List<Project> currentProjects = ProjectController.getProjectsHandledByOfficer(officer_id);
        boolean isOverlapping = currentProjects.stream().noneMatch(p ->
                p.getAppDateOpen().isBefore(newProject.getAppDateClose()) &&
                p.getAppDateClose().isAfter(newProject.getAppDateOpen()) &&
                p.isVisibility());
        return (isOverlapping && projectApplications.isEmpty());
    }

    public static boolean createProjectRegistration(String project_id, String id) {
        String regID = getProjectApplicationsRepository().generateId("REG");
        String project_name = ProjectController.getProjectByID(project_id).getProjectName();
        ProjectRegistration projectRegistration = new ProjectRegistration(
                regID,
                project_id,
                project_name,
                id,
                RegistrationStatus.PENDING
        );
        return getProjectRegistrationRepository().create(projectRegistration);

    }

    public static List<ProjectRegistration> getProjectRegistrationByOfficerId(String id) {
        return getProjectRegistrationRepository().getByFilter(projectRegistration
                -> projectRegistration.getOfficerId().equalsIgnoreCase(id));
    }
}
