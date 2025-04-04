package Project;

import Project.ProjectRepository;
import Project.Project;

import java.util.ArrayList;


public class ProjectController {
    private static ProjectRepository getprojectRepository() {
        return new ProjectRepository("./src/data/ProjectList.csv");
    }

    public static ArrayList<Project> getAllProjects() {
        return getprojectRepository().getAllProjects();
    }

    public static void showProjects(){
        ProjectBoundary.displayProjects();
    }
}



