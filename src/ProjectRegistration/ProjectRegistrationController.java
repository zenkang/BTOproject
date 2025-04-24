package ProjectRegistration;

import Enumerations.RegistrationStatus;

import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static Utils.RepositoryGetter.getProjectApplicationsRepository;
import static Utils.RepositoryGetter.getProjectRegistrationRepository;

/**
 * Controller class for handling logic related to officer registrations
 * for BTO projects. Provides functionality for creating, updating, and validating
 * project registration records.
 */
public class ProjectRegistrationController {

    /**
     * Checks if an officer is eligible to register for a project.
     * Criteria include: not having already applied to the project,
     * not currently handling overlapping active projects,
     * and availability of officer slots.
     *
     * @param officer_id the officer's ID
     * @param project_id the ID of the project
     * @return {@code true} if eligible, {@code false} otherwise
     */
    public static boolean canRegisterForProject(String officer_id, String project_id) {
        //check officer never applied to the project
        List<ProjectApplication> projectApplications = ProjectApplicationController.getApplicationsByApplicantID(officer_id);
        projectApplications = projectApplications.stream()
                .filter(p -> p.getProjectID().equalsIgnoreCase(project_id)).toList();
        //check officer isnt registered to any active projects
        Project newProject = ProjectController.getProjectByID(project_id);
        List<Project> currentProjects = ProjectController.getProjectsHandledByOfficer(officer_id);
        boolean isOverlapping = currentProjects.stream().noneMatch(p ->
                p.getAppDateOpen().isBefore(newProject.getAppDateClose()) &&
                p.getAppDateClose().isAfter(newProject.getAppDateOpen()) &&
                p.isVisibility());

        return isOverlapping && projectApplications.isEmpty() && (newProject.getNoOfficersSlots()>0);
    }

    /**
     * Creates a new project registration record with PENDING status.
     *
     * @param project_id the ID of the project to register for
     * @param id the officer's ID
     * @return {@code true} if the registration is successfully created, {@code false} otherwise
     */
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

    /**
     * Retrieves all registration records for a given officer.
     *
     * @param id the officer's ID
     * @return a list of {@code ProjectRegistration} instances submitted by the officer
     */
    public static List<ProjectRegistration> getProjectRegistrationByOfficerId(String id) {
        return getProjectRegistrationRepository().getByFilter(projectRegistration
                -> projectRegistration.getOfficerId().equalsIgnoreCase(id));
    }

    /**
     * Retrieves all project registration requests submitted for projects created by a given manager.
     *
     * @param id the manager's ID
     * @return a list of {@code ProjectRegistration} instances related to the manager's projects
     */
    public static List<ProjectRegistration> getHandledProjectRegistration(String id) {
        List<Project> projects = ProjectController.getProjectsCreatedByManager(id);
        List<String> projectNames = projects.stream().map(Project::getID).toList();
        return getProjectRegistrationRepository().getAll()
                .stream()
                .filter(p -> projectNames.contains(p.getProjectID()))
                .toList();
    }

    /**
     * Updates the status of a project registration (e.g., from PENDING to APPROVED/REJECTED).
     *
     * @param registerID the registration ID
     * @param status the new {@code RegistrationStatus}
     * @return {@code true} if update was successful, {@code false} otherwise
     */
    public static boolean updateProjectRegistration(String registerID, RegistrationStatus status) {
        ProjectRegistration projectRegistration = getProjectRegistrationRepository().getByID(registerID);
        projectRegistration.setStatus(status);

        return getProjectRegistrationRepository().update(projectRegistration);

    }

    /**
     * Retrieves a project registration by its ID.
     *
     * @param id the registration ID
     * @return the {@code ProjectRegistration} object or {@code null} if not found
     */
    public static ProjectRegistration getProjectRegistrationByID(String id) {
        return getProjectRegistrationRepository().getByID(id);
    }

    /**
     * Checks if an officer has already registered for a specific project with PENDING status.
     *
     * @param id the officer's ID
     * @param projectID the project ID
     * @return {@code true} if a PENDING registration exists, {@code false} otherwise
     */
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

    /**
     * Checks if the officer associated with a registration is currently assigned to
     * another project whose application close date is in the future (active).
     *
     * @param registerID the registration ID
     * @return {@code true} if officer is still in an active project, {@code false} otherwise
     */
    public static boolean checkOfficerInActiveProject(String registerID) {
        LocalDate currentDate = LocalDate.now();
        ProjectRegistration projectRegistration = getProjectRegistrationRepository().getByID(registerID);
        String officer_id = projectRegistration.getOfficerId();
        List<Project> projects = ProjectController.getProjectsHandledByOfficer(officer_id);
        for(Project project:projects){
            if(currentDate.isBefore(project.getAppDateClose())){
                return true;
            }
        }
        return false;

    }
}
