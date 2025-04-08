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
<<<<<<< HEAD
    public static Project getProjectByName(String projectName) {
    	ArrayList<Project> projects = getAllProjects();
    	for (Project p : projects) {
            if (p.getProjectName().equalsIgnoreCase(projectName)) {
                return p;
            }
        }
        return null;
    }

=======
>>>>>>> d2031f6cce777e80ef284d1c18a1715bf0700fef
}



