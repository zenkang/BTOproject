package Project;

import Applicant.Applicant;
import Enumerations.MaritalStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class ProjectController {
    public static void showProjectModificationsMenu() {
        ProjectBoundary projectBoundary = new ProjectBoundary();
        ProjectBoundary.displayProjectMenu();
    }
    private static ProjectRepository retrieveProjectRepository() {
        return new ProjectRepository("./src/data/ProjectList.csv");
    }

    public static ArrayList<Project> getAllProjects() {
        return retrieveProjectRepository().getAllProjects();
    }

    public static void displayAllProjects(){
        ProjectBoundary projectBoundary = new ProjectBoundary();
        ProjectBoundary.displayProjects();
    }
    public static void displayProjectForApplicant(Applicant applicant){
        ProjectBoundary projectBoundary = new ProjectBoundary();
        projectBoundary.displayProjectsForApplicant(applicant);
    }

    public static void displayProjectsCreatedByManager(String managerName){
        ProjectBoundary projectBoundary = new ProjectBoundary();
        ProjectBoundary.displayProjectsCreatedByManager(managerName);
    }

    public static List<Project> getProjectsForApplicant(Applicant applicant) {
        ProjectRepository repo = retrieveProjectRepository();
        if (applicant.getMaritalStatus() == MaritalStatus.SINGLE) {
            return repo.getByFilter(project ->
                    project.getType1().equalsIgnoreCase("2-Room") ||
                            project.getType2().equalsIgnoreCase("2-Room")
            );
        } else if (applicant.getMaritalStatus() == MaritalStatus.MARRIED) {
            return repo.getAllProjects();
        } else {
            return repo.getAllProjects();
        }
    }

    public static Project getProjectByName(String projectName) {
        ProjectRepository projectRepository = retrieveProjectRepository();
        return projectRepository.getByProjectName(projectName);
    }

    public static Project getProjectByID(String projectID) {
        ProjectRepository projectRepository = retrieveProjectRepository();
        return projectRepository.getByProjectID(projectID);
    }

    public static List<Project> getFilteredAndSortedProjects(String location, String flatType) {
        ProjectRepository repo = retrieveProjectRepository();
        // Use getByFilter to return projects that meet the filtering criteria.
        List<Project> filtered = repo.getByFilter(project -> {
            boolean matches = true;
            // If a location filter is provided, verify the project's neighbourhood matches.
            if (!location.isEmpty()) {
                matches &= project.getNeighbourhood().equalsIgnoreCase(location);
            }
            // If a flat type filter is provided, verify the project offers that room type.
            if (!flatType.isEmpty()) {
                matches &= (project.getType1().equalsIgnoreCase(flatType) ||
                        project.getType2().equalsIgnoreCase(flatType));
            }
            return matches;
        });
        // Then sort by project name (alphabetical order) using a comparator.
        filtered.sort(Comparator.comparing(Project::getProjectName, String.CASE_INSENSITIVE_ORDER));
        return filtered;
    }

    public static List<Project> getProjectsCreatedByManager(String managerName) {
        ProjectRepository repo = retrieveProjectRepository();
        return repo.getByFilter(project -> project.getManager().equalsIgnoreCase(managerName));
    }

    public static void updateProjectName(String projectID, String newProjectName) {
            ProjectRepository projectRepository = retrieveProjectRepository();
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
        ProjectRepository projectRepository = retrieveProjectRepository();
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
        ProjectRepository projectRepository = retrieveProjectRepository();
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
        ProjectRepository projectRepository = retrieveProjectRepository();
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
        ProjectRepository projectRepository = retrieveProjectRepository();
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
        ProjectRepository projectRepository = retrieveProjectRepository();
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

    public static void updateSellPriceOfRoomType1(String projectID, int newSellPrice){
        ProjectRepository projectRepository = retrieveProjectRepository();
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

    public static void updateSellPriceOfRoomType2(String projectID, int newSellPrice){
        ProjectRepository projectRepository = retrieveProjectRepository();
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

    public static void updateProjectApplicationOpenDate(String projectID, String newAppOpenDate) {
        ProjectRepository projectRepository = retrieveProjectRepository();
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

    public static void updateProjectApplicationCloseDate(String projectID, String newAppCloseDate) {
        ProjectRepository projectRepository = retrieveProjectRepository();
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
        ProjectRepository projectRepository = retrieveProjectRepository();
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
        ProjectRepository projectRepository = retrieveProjectRepository();
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
        ProjectRepository projectRepository = retrieveProjectRepository();
        return projectRepository.deleteProjectByID(projectID);
    }

    public static boolean createProject(Project newProject){
        ProjectRepository repository = retrieveProjectRepository();
        return repository.create(newProject);
    }



}




