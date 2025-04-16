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

    public static ArrayList<Project> getAllProjects() {
        return getProjectRepository().getAll();
    }
    public static Project getProjectByName(String projectName) {
        ProjectRepository projectRepository = getProjectRepository();
        return projectRepository.getByProjectName(projectName);
    }
    public static Project getProjectByID(String projectID) {
        ProjectRepository projectRepository = getProjectRepository();
        return projectRepository.getByProjectID(projectID);
    }
    public static void updateProjectName(String projectID, String newProjectName) {
            ProjectRepository projectRepository = getProjectRepository();
            Project project = projectRepository.getByID(projectID);

            if (project == null) {
                System.out.println("No project found with the ID: " + projectID);
                return;
            }

            project.setProjectName(newProjectName);

            boolean result = projectRepository.update(project);
            if (result) {
                System.out.println("Project name updated successfully.");
            } else {
            System.out.println("Update failed.");
            }

            }
    public static void updateProjectNeighbourhood(String projectID, String newProjectNeighbourhood) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);

        if (project == null) {
            System.out.println("No project found with the ID: " + projectID);
            return;
        }
        project.setProjectNeighbourhood(newProjectNeighbourhood);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Neighbourhood updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateProjectRoomType1(String projectID, String newRoomType1) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);

        if (project == null) {
            System.out.println("No project found with the ID: " + projectID);
            return;
        }
        project.setProjectRoomType1(newRoomType1);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Room Type 1 updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateProjectRoomType2(String projectID, String newRoomType2) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);

        if (project == null) {
            System.out.println("No project found with the ID: " + projectID);
            return;
        }
        project.setProjectRoomType2(newRoomType2);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Room Type 2 updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateProjectNumOfRoomType1(String projectID, int newNumOfRoomType1) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);

        if (project == null) {
            System.out.println("No project found with the ID: " + projectID);
            return;
        }
        project.setProjectNumOfType1(newNumOfRoomType1);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Number of Room Type 1 updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateProjectNumOfRoomType2(String projectID, int newNumOfRoomType2) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);

        if (project == null) {
            System.out.println("No project found with the name: " + projectID);
            return;
        }
        project.setProjectNumOfType2(newNumOfRoomType2);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Number of Number of Room Type 2 updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateSellPriceOfRoomType1(String projectID, double newSellPrice){
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);

        if (project == null) {
            System.out.println("No project found with the name: " + projectID);
            return;
        }
        project.setSellPriceType1(newSellPrice);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Selling Price of Type 1 Rooms updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateSellPriceOfRoomType2(String projectID, double newSellPrice){
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);

        if (project == null) {
            System.out.println("No project found with the name: " + projectID);
            return;
        }
        project.setSellPriceType2(newSellPrice);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Selling Price of Type 2 Rooms updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateProjectApplicationOpenDate(String projectID, LocalDate newAppOpenDate) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);

        if (project == null) {
            System.out.println("No project found with the ID: " + projectID);
            return;
        }
        project.setProjectApplicationOpenData(newAppOpenDate);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Application Open Date updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateProjectApplicationCloseDate(String projectID, LocalDate newAppCloseDate) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);

        if (project == null) {
            System.out.println("No project found with the ID: " + projectID);
            return;
        }
        project.setProjectApplicationCloseDate(newAppCloseDate);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Application Closing Date updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateProjectManager(String projectID, String newProjectManager) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);

        if (project == null) {
            System.out.println("No project found with the ID: " + projectID);
            return;
        }
        project.setProjectManager(newProjectManager);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Manager updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateProjectNumOfOfficerSlots(String projectID, int newProjectNumOfOfficerSlots) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);

        if (project == null) {
            System.out.println("No project found with the name: " + projectID);
            return;
        }
        project.setProjectNumOfOfficers(newProjectNumOfOfficerSlots);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Number of Officer Slots updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static boolean deleteProject(String projectID) {
        ProjectRepository projectRepository = getProjectRepository();
        return projectRepository.deleteProjectByID(projectID);
    }
    public static boolean createProject(Project newProject){
        ProjectRepository repository = getProjectRepository();
        return repository.create(newProject);
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
        String newID = repository.generateId();
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

    public static void updateProjectVisibility(String projectID, boolean b) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByProjectID(projectID);
        if (project == null) {
            System.out.println("No project found with the name: " + projectID);
        }
        assert project != null;
        project.setVisibility(b);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Visibility updated successfully.");
        }
        else{
            System.out.println("Update failed.");
        }
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
            return repo.getAll();
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




