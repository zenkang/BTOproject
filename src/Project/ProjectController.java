package Project;

import Abstract.IUserProfile;
import Applicant.Applicant;
import Enumerations.MaritalStatus;
import Officer.Officer;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;
import ProjectRegistration.ProjectRegistration;
import ProjectRegistration.ProjectRegistrationController;

import java.time.LocalDate;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static Utils.RepositoryGetter.getProjectRepository;

/**
 * The ProjectController class provides methods to manage BTO projects in the system.
 * It handles CRUD operations, filtering, and project status updates related to managers, officers, and applicants.
 */
public class ProjectController {


    /**
     * Retrieves a project by its name (case-insensitive).
     *
     * @param projectName the name of the project
     * @return the Project object if found, else null
     */
    public static Project getProjectByName(String projectName) {
        ArrayList<Project> p = getProjectRepository().getAll();
        for (Project p1 : p) {
            if (p1.getProjectName().equalsIgnoreCase(projectName)) {
                return p1;
            }
        }
        return null;
    }

    /**
     * Retrieves a project by its unique ID.
     *
     * @param projectID the ID of the project
     * @return the Project object if found, else null
     */
    public static Project getProjectByID(String projectID) {
        return getProjectRepository().getByID(projectID);
    }

    /**
     * Updates the name of a project.
     *
     * @param projectID the ID of the project
     * @param newProjectName the new name for the project
     * @return true if update was successful, false otherwise
     */
    public static boolean updateProjectName(String projectID, String newProjectName) {
            ProjectRepository projectRepository = getProjectRepository();
            Project project = projectRepository.getByID(projectID);
            project.setProjectName(newProjectName);
            return projectRepository.update(project);

    }
    public static boolean updateProjectNeighbourhood(String projectID, String newProjectNeighbourhood) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectNeighbourhood(newProjectNeighbourhood);
        return projectRepository.update(project);
    }
    public static boolean updateProjectRoomType1(String projectID, String newRoomType1) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectRoomType1(newRoomType1);
        return projectRepository.update(project);
    }
    public static boolean updateProjectRoomType2(String projectID, String newRoomType2) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectRoomType2(newRoomType2);
        return projectRepository.update(project);
    }
    public static boolean updateProjectNumOfRoomType1(String projectID, int newNumOfRoomType1) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectNumOfType1(newNumOfRoomType1);
        return projectRepository.update(project);
    }
    public static boolean updateProjectNumOfRoomType2(String projectID, int newNumOfRoomType2) {
        Project project = getProjectRepository().getByID(projectID);
        project.setProjectNumOfType2(newNumOfRoomType2);
        return getProjectRepository().update(project);
    }
    public static boolean updateSellPriceOfRoomType1(String projectID, double newSellPrice){
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setSellPriceType1(newSellPrice);
        return projectRepository.update(project);
    }
    public static boolean updateSellPriceOfRoomType2(String projectID, double newSellPrice){
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setSellPriceType2(newSellPrice);
        return projectRepository.update(project);
    }
    public static boolean updateProjectApplicationOpenDate(String projectID, LocalDate newAppOpenDate) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectApplicationOpenData(newAppOpenDate);
        return projectRepository.update(project);
    }
    public static boolean updateProjectApplicationCloseDate(String projectID, LocalDate newAppCloseDate) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectApplicationCloseDate(newAppCloseDate);
        return projectRepository.update(project);
    }
    public static boolean updateProjectManager(String projectID, String newProjectManager) {
        Project project = getProjectRepository().getByID(projectID);
        project.setProjectManager(newProjectManager);
        return getProjectRepository().update(project);
    }
    public static boolean deleteProject(String projectID) {
        Project project = getProjectRepository().getByID(projectID);
        return getProjectRepository().delete(project);
    }

    /**
     * Creates a new project and saves it to the repository.
     *
     * @return true if creation was successful, false otherwise
     */
    public static boolean createProject(String projectName,
                                        String neighbourhood,
                                        String roomType1,
                                        int noOfUnitsType1,
                                        double sellPriceType1,
                                        String roomType2,
                                        int noOfUnitsType2,
                                        double sellPriceType2,
                                        LocalDate appDateOpen,
                                        LocalDate appDateClose,
                                        String managerID,
                                        int noOfficersSlots,
                                        boolean visible){
        ProjectRepository repository = getProjectRepository();
        String newID = repository.generateId("PR");
        Project newProject = new Project(newID,projectName, neighbourhood, roomType1, noOfUnitsType1, sellPriceType1, roomType2, noOfUnitsType2, sellPriceType2,
                appDateOpen, appDateClose, managerID, noOfficersSlots, visible);
        return repository.create(newProject);
    }

    /**
     * Checks whether a project name is unique (not already used).
     *
     * @param projectName the project name to check
     * @return true if name is unique, false otherwise
     */
    public static boolean checkUniqueProjectName(String projectName) {
        List<Project> p = getProjectRepository().getByFilter(project -> project.getProjectName().equalsIgnoreCase(projectName));
        return p.isEmpty();
    }

    /**
     * Checks whether the manager currently has any active projects.
     *
     * @param managerID the manager's ID
     * @return true if no active project exists (i.e., can create new one), false otherwise
     */
    public static boolean checkActiveProject(String managerID) {
        List<Project> p = getProjectRepository().getByFilter(project -> (project.getManagerID().equalsIgnoreCase(managerID))&&project.isVisibility());
        return p.isEmpty();
    }

    /**
     * Updates the visibility of the specified project.
     *
     * @param project the project to update
     * @param b the new visibility status
     * @return true if update was successful, false otherwise
     */
    public static boolean updateProjectVisibility(Project project, boolean b) {
        project.setVisibility(b);
        return getProjectRepository().update(project);
    }

    /**
     * Retrieves all projects created by a specific manager.
     *
     * @param managerID the manager's ID
     * @return list of projects created by the manager
     */
    public static List<Project> getProjectsCreatedByManager(String managerID) {
        ProjectRepository repo = getProjectRepository();
        return repo.getByFilter(project -> project.getManagerID().equalsIgnoreCase(managerID));
    }

    public static List<Project> getFilteredProjectsByManager(String managerID,Predicate<Project> Filter ) {
        List<Project> projects = getProjectsCreatedByManager(managerID);
        if(Filter == null){
            return getProjectsCreatedByManager(managerID);
        }
        else{
            return projects.stream().filter(Filter).toList();
        }
    }

    /**
     * Gets a list of projects eligible for viewing or application by the applicant,
     * based on age, marital status, and previous application/registration.
     *
     * @param applicant the applicant (implements IUserProfile)
     * @return list of eligible projects
     */
    public static  <T extends IUserProfile> List<Project> getProjectsForApplicant(T applicant) {
        ProjectRepository repo = getProjectRepository();
        List<Project> list;
        if (applicant.getMaritalStatus() == MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            list = repo.getByFilter(project ->
                    ((project.getType1().equalsIgnoreCase("2-Room") && project.getNoOfUnitsType1()>0)||
                            (project.getType2().equalsIgnoreCase("2-Room") && project.getNoOfUnitsType2()>0))
                            && project.isVisibility()
            );
        } else if (applicant.getMaritalStatus() == MaritalStatus.MARRIED && applicant.getAge() >= 21) {
            list = repo.getByFilter(project -> project.isVisibility()&& (project.getNoOfUnitsType2()> 0 || project.getNoOfUnitsType1()> 0));
        } else {
            return Collections.emptyList();
        }
        List<ProjectApplication> applications =
                ProjectApplicationController.getApplicationsByApplicantID(applicant.getNric());
        List<ProjectRegistration> registrations =
                ProjectRegistrationController.getProjectRegistrationByOfficerId(applicant.getNric());
        if(applications.isEmpty() && registrations.isEmpty()) {
            return list;
        }
        // 2) collect all the project‑IDs this applicant has already applied/registered to
        Set<String> appliedIds = new HashSet<>();
        for (ProjectApplication app : applications) {
            appliedIds.add(app.getProjectID());
        }
        for (ProjectRegistration reg : registrations) {
            appliedIds.add(reg.getProjectID());
        }
        // remove all projects whose ID is in appliedIds
        return list.stream()
                .filter(p -> !appliedIds.contains(p.getID()))
                .toList();
    }

    /**
     * Gets project IDs based on eligibility rules for the given applicant.
     *
     * @param applicant the applicant
     * @return list of project IDs
     */
    public static  <T extends IUserProfile> List<String> getProjectIDsForApplicant(T applicant) {
        List<Project> projects = getProjectsForApplicant(applicant);
        assert projects != null;
        return projects.stream()
                .map(Project::getID)
                .distinct()
                .toList();
    }

    /**
     * Filters a list of applicant-eligible projects based on custom predicate.
     *
     * @param Applicant the applicant
     * @param Filter predicate to apply for filtering
     * @return filtered project list
     */
    public static <T extends IUserProfile> List<Project> getFilteredProjectsForApplicant(T Applicant,Predicate<Project> Filter) {
        List<Project> projects = getProjectsForApplicant(Applicant);
        if(Filter == null){
            return getProjectsForApplicant(Applicant);
        }
        else{
            return projects.stream().filter(Filter).toList();
        }
    }

    /**
     * Gets filtered list of projects that an officer can register for.
     *
     * @param officer the officer
     * @param Filter optional predicate filter
     * @return filtered project list
     */
    public static List<Project> getFilteredProjectsForRegistration(Officer officer,Predicate<Project> Filter) {
        List<Project> projects = ProjectController.getFilteredProjects(project -> project.isVisibility());

        List<ProjectApplication> applications =
                ProjectApplicationController.getApplicationsByApplicantID(officer.getNric());
        Set<String> appliedIds = new HashSet<>();
        for (ProjectApplication app : applications) {
            appliedIds.add(app.getProjectID());
        }
        List<Project> list = projects.stream()
                .filter(p -> !appliedIds.contains(p.getID()))
                .toList();
        if(Filter == null){
            return list;
        }
        else{
            return projects.stream().filter(Filter).toList();
        }
    }

    /**
     * Returns all projects matching a specified predicate.
     *
     * @param Filter predicate to apply
     * @return list of matching projects
     */
    public static List<Project> getFilteredProjects(Predicate<Project> Filter) {
        ProjectRepository repo = getProjectRepository();
        List<Project> filteredProjects;
        if(Filter == null){
            filteredProjects = repo.getAll();

        }
        else{
            filteredProjects = repo.getByFilter(Filter);
        }
        filteredProjects = new ArrayList<>(filteredProjects);
        filteredProjects.sort(Comparator.comparing(Project::getID, String.CASE_INSENSITIVE_ORDER));
        return filteredProjects;
    }

    public static List<String> getProjectIDsManagedBy(String managerID) {
        return getProjectRepository().getAll().stream()
                .filter(p -> p.getManagerID().equalsIgnoreCase(managerID))
                .map(Project::getID)
                .toList();
    }
    public static List<Project> getProjectsByIDs(List<String> projectIds) {
        return projectIds.stream()
                .map(ProjectController::getProjectByID)
                .filter(Objects::nonNull) // Skip any IDs that don't return a project
                .collect(Collectors.toList());
    }


    public static boolean isHandlingProject(String id, String projectID) {
        ProjectRepository repo = getProjectRepository();
        List<Project> list;
        list = repo.getByFilter(project -> project.getID().equalsIgnoreCase(projectID)
        && Arrays.asList(project.getOfficer()).contains(id) && LocalDate.now().isBefore(project.getAppDateClose()));
        return !list.isEmpty();
    }

    public static List<String> getProjectNamesHandledByOfficer(String id) {
        return getProjectRepository().getAll().stream()
                .filter(p -> Arrays.asList(p.getOfficer()).contains(id))
                .map(Project::getProjectName)
                .toList();
    }
    public static List<Project> getProjectsHandledByOfficer(String id) {
        return getProjectRepository().getAll().stream()
                .filter(p -> p.getOfficer().contains(id))
                .toList();
    }

    /**
     * Updates the assigned officer list when a registration is approved.
     *
     * @param registerID the ID of the registration
     */
    public static void updateOfficer(String registerID) {
        ProjectRegistration registration = ProjectRegistrationController.getProjectRegistrationByID(registerID);
        String officerID = registration.getOfficerId();
        Project project = getProjectRepository().getByID(registration.getProjectID());
        project.getOfficer().add(officerID);
        int officerSlots = project.getNoOfficersSlots();
        officerSlots-=1;
        project.setProjectNumOfOfficers(officerSlots);
        getProjectRepository().update(project);
    }
}




