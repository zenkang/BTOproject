package Project;

import Applicant.Applicant;
import Enumerations.MaritalStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static Utils.RepositoryGetter.getProjectRepository;


public class ProjectController {

    public static List<String> getUniqueProjectNames() {
       return getProjectRepository().getAll()
        .stream()
        .map(Project::getProjectName)
        .distinct()
        .toList();
    }


    public static Project getProjectByName(String projectName) {
        ProjectRepository projectRepository = getProjectRepository();
        return projectRepository.getByProjectName(projectName);
    }
    public static Project getProjectByID(String projectID) {
        ProjectRepository projectRepository = getProjectRepository();
        return projectRepository.getByProjectID(projectID);
    }
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
        Project project = projectRepository.getByProjectID(projectID);
        project.setProjectRoomType1(newRoomType1);
        return projectRepository.update(project);
    }
    public static boolean updateProjectRoomType2(String projectID, String newRoomType2) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        project.setProjectRoomType2(newRoomType2);
        return projectRepository.update(project);
    }
    public static boolean updateProjectNumOfRoomType1(String projectID, int newNumOfRoomType1) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        project.setProjectNumOfType1(newNumOfRoomType1);
        return projectRepository.update(project);
    }
    public static boolean updateProjectNumOfRoomType2(String projectID, int newNumOfRoomType2) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        project.setProjectNumOfType2(newNumOfRoomType2);
        return projectRepository.update(project);
    }
    public static boolean updateSellPriceOfRoomType1(String projectID, double newSellPrice){
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        project.setSellPriceType1(newSellPrice);
        return projectRepository.update(project);
    }
    public static boolean updateSellPriceOfRoomType2(String projectID, double newSellPrice){
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        project.setSellPriceType2(newSellPrice);
        return projectRepository.update(project);
    }
    public static boolean updateProjectApplicationOpenDate(String projectID, LocalDate newAppOpenDate) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        project.setProjectApplicationOpenData(newAppOpenDate);
        return projectRepository.update(project);
    }
    public static boolean updateProjectApplicationCloseDate(String projectID, LocalDate newAppCloseDate) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        project.setProjectApplicationCloseDate(newAppCloseDate);
        return projectRepository.update(project);
    }
    public static boolean updateProjectManager(String projectID, String newProjectManager) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        project.setProjectManager(newProjectManager);
        return projectRepository.update(project);
    }
    public static boolean updateProjectNumOfOfficerSlots(String projectID, int newProjectNumOfOfficerSlots) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        project.setProjectNumOfOfficers(newProjectNumOfOfficerSlots);
        return projectRepository.update(project);
    }
    public static boolean deleteProject(String projectID) {
        ProjectRepository projectRepository = getProjectRepository();
        return projectRepository.deleteProjectByID(projectID);
    }


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
                                        String manager_name,
                                        int noOfficersSlots,
                                        String[] officers,
                                        boolean visible){
        ProjectRepository repository = getProjectRepository();
        String newID = repository.generateId("PR");
        Project newProject = new Project(newID,projectName, neighbourhood, roomType1, noOfUnitsType1, sellPriceType1, roomType2, noOfUnitsType2, sellPriceType2,
                appDateOpen, appDateClose, manager_name, noOfficersSlots, officers,visible);
        return repository.create(newProject);
    }


    public static boolean checkUniqueProjectName(String projectName) {
        ProjectRepository projectRepository = getProjectRepository();
        return projectRepository.checkUniqueProjectName(projectName);
    }

    public static boolean checkActiveProject(String manager_name) {
        ProjectRepository projectRepository = getProjectRepository();
        return projectRepository.checkActiveProject(manager_name);
    }

    public static boolean updateProjectVisibility(String projectID, boolean b) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        assert project != null;
        project.setVisibility(b);
        return projectRepository.update(project);
    }

    public static List<Project> getProjectsCreatedByManager(String managerName) {
        ProjectRepository repo = getProjectRepository();
        return repo.getByFilter(project -> project.getManager().equalsIgnoreCase(managerName));
    }


    public static List<Project> getProjectsForApplicant(Applicant applicant) {
        ProjectRepository repo = getProjectRepository();
        if (applicant.getMaritalStatus() == MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            return repo.getByFilter(project ->
                    (project.getType1().equalsIgnoreCase("2-Room") ||
                            project.getType2().equalsIgnoreCase("2-Room"))
                            && project.isVisibility()
            );
        } else if (applicant.getMaritalStatus() == MaritalStatus.MARRIED && applicant.getAge() >= 21) {
            return repo.getByFilter(project -> project.isVisibility());
        } else {
            return null;
        }
    }

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

    public static List<Project> getProjectsManagedBy(String managerName) {
        return getProjectRepository().getAll().stream()
                .filter(project -> project.getManager().equalsIgnoreCase(managerName))
                .toList();
    }

}




