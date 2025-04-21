package ProjectRegistration;

import Enumerations.RegistrationStatus;

import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static Utils.RepositoryGetter.getProjectApplicationsRepository;
import static Utils.RepositoryGetter.getProjectRegistrationRepository;

public class ProjectRegistrationController {
    public static boolean canRegisterForProject(String officer_id, String project_id) {
        List<ProjectApplication> projectApplications = ProjectApplicationController.getApplicationsByApplicantID(officer_id);
        projectApplications = projectApplications.stream()
                .filter(p -> p.getProjectID().equalsIgnoreCase(project_id)).toList();

        Project newProject = ProjectController.getProjectByID(project_id);
        List<Project> currentProjects = ProjectController.getProjectsHandledByOfficer(officer_id);
        boolean isOverlapping = currentProjects.stream().noneMatch(p ->
                p.getAppDateOpen().isBefore(newProject.getAppDateClose()) &&
                p.getAppDateClose().isAfter(newProject.getAppDateOpen()) &&
                p.isVisibility());
        return isOverlapping && projectApplications.isEmpty();
    }

    public static boolean createProjectRegistration(String project_id, String id) {
        String regID = getProjectRegistrationRepository().generateId("REG");
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

    public static List<ProjectRegistration> getHandledProjectRegistration(String id) {
        List<Project> projects = ProjectController.getProjectsCreatedByManager(id);
        List<String> projectNames = projects.stream().map(Project::getID).toList();
        return getProjectRegistrationRepository().getAll()
                .stream()
                .filter(p -> projectNames.contains(p.getProjectID()))
                .toList();
    }

    public static boolean updateProjectRegistration(String registerID, RegistrationStatus status) {
        ProjectRegistration projectRegistration = getProjectRegistrationRepository().getByID(registerID);
        projectRegistration.setStatus(status);

        return getProjectRegistrationRepository().update(projectRegistration);

    }
    public static ProjectRegistration getProjectRegistrationByID(String id) {
        return getProjectRegistrationRepository().getByID(id);
    }

    public static boolean hasRegisteredForProject(String id, String projectID) {
        List<ProjectRegistration> projectRegistration = getProjectRegistrationRepository().getByFilter(
                p -> p.status.equals(RegistrationStatus.PENDING) &&
                        p.getOfficerId().equalsIgnoreCase(id) &&
                        p.getProjectID().equalsIgnoreCase(projectID));
        if(projectRegistration.isEmpty()){
            return false;
        }
        return true;

    }
}
