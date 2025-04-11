package Project;

import java.util.ArrayList;


public class ProjectController {
    public static void showProjectModificationsMenu() {
        ProjectBoundary projectBoundary = new ProjectBoundary();
        projectBoundary.displayProjectMenu();
    }

    private static ProjectRepository retrieveProjectRepository() {
        return new ProjectRepository("./src/data/ProjectList.csv");
    }

    public static ArrayList<Project> getAllProjects() {
        return retrieveProjectRepository().getAllProjects();
    }
    public static void displayAllProjects(){
        ProjectBoundary projectBoundary = new ProjectBoundary();
        projectBoundary.displayProjects();
    }

    public static Project getProjectByName(String projectName) {
        ProjectRepository projectRepository = retrieveProjectRepository();
        return projectRepository.getByProjectName(projectName);
    }
    public static Project getProjectByID(String projectID) {
        ProjectRepository projectRepository = retrieveProjectRepository();
        return projectRepository.getByProjectID(projectID);
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




