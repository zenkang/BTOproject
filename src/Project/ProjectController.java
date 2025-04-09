package Project;

import Project.ProjectRepository;
import Project.Project;
import Project.ProjectBoundary;

import java.util.ArrayList;


public class ProjectController {
    public static void showProjectMenu() {
        ProjectBoundary projectBoundary = new ProjectBoundary();
        projectBoundary.displayProjectMenu();
    }

    private static ProjectRepository getprojectRepository() {
        return new ProjectRepository("./src/data/ProjectList.csv");
    }

    public static ArrayList<Project> getAllProjects() {
        return getprojectRepository().getAllProjects();
    }


    public static Project getProjectByName(String projectName) {
        ProjectRepository projectRepository = getprojectRepository();
        return projectRepository.getByProjectName(projectName);
    }

    public static void updateProjectName(String projectID, String newProjectName) {
            ProjectRepository projectRepository = getprojectRepository();
            Project project = projectRepository.getByID(projectID);

            if (project == null) {
                System.out.println("No project found with the ID: " + projectID);
                return;
            }

            Project duplicateProject = projectRepository.getByProjectName(newProjectName);
            if (duplicateProject != null) {
                System.out.println("A project with the name " + newProjectName + " already exists.");
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
    public static void updateProjectNeighbourhood(String projectName, String newProjectNeighbourhood) {
        ProjectRepository projectRepository = getprojectRepository();
        Project project = projectRepository.getByProjectName(projectName);

        if (project == null) {
            System.out.println("No project found with the name: " + projectName);
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
    public static void updateProjectRoomType1(String projectName, String newRoomType1) {
        ProjectRepository projectRepository = getprojectRepository();
        Project project = projectRepository.getByProjectName(projectName);

        if (project == null) {
            System.out.println("No project found with the name: " + projectName);
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
    public static void updateProjectRoomType2(String projectName, String newRoomType2) {
        ProjectRepository projectRepository = getprojectRepository();
        Project project = projectRepository.getByProjectName(projectName);

        if (project == null) {
            System.out.println("No project found with the name: " + projectName);
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
    public static void updateProjectNumOfRoomType1(String projectName, int newNumOfRoomType1) {
        ProjectRepository projectRepository = getprojectRepository();
        Project project = projectRepository.getByProjectName(projectName);

        if (project == null) {
            System.out.println("No project found with the name: " + projectName);
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
    public static void updateNumOfRoomType2(String projectName, int newNumOfRoomType2) {
        ProjectRepository projectRepository = getprojectRepository();
        Project project = projectRepository.getByProjectName(projectName);

        if (project == null) {
            System.out.println("No project found with the name: " + projectName);
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
    public static void updateProjectApplicationOpenDate(String projectName, String newAppOpenDate) {
        ProjectRepository projectRepository = getprojectRepository();
        Project project = projectRepository.getByProjectName(projectName);

        if (project == null) {
            System.out.println("No project found with the name: " + projectName);
            return;
        }
        project.setProjectApplicationOpenData(newAppOpenDate);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Application Open Date updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }public static void updateProjectApplicationCloseDate(String projectName, String newAppCloseDate) {
        ProjectRepository projectRepository = getprojectRepository();
        Project project = projectRepository.getByProjectName(projectName);

        if (project == null) {
            System.out.println("No project found with the name: " + projectName);
            return;
        }
        project.setProjectNeighbourhood(newAppCloseDate);
        boolean result = projectRepository.update(project);
        if (result) {
            System.out.println("Project Application Closing Date updated successfully.");
        } else {
            System.out.println("Update failed.");
        }

    }
    public static void updateProjectManager(String projectName, String newProjectManager) {
        ProjectRepository projectRepository = getprojectRepository();
        Project project = projectRepository.getByProjectName(projectName);

        if (project == null) {
            System.out.println("No project found with the name: " + projectName);
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
    public static void updateProjectNumOfOfficerSlots(String projectName, int newProjectNumOfOfficerSlots) {
        ProjectRepository projectRepository = getprojectRepository();
        Project project = projectRepository.getByProjectName(projectName);

        if (project == null) {
            System.out.println("No project found with the name: " + projectName);
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
    }



