package Project;

import Project.Project;
import Utils.SafeScanner;

import java.util.ArrayList;
import java.util.Scanner;

public class ProjectBoundary {
    public static void displayProjects(){
        ArrayList<Project> projects = ProjectController.getAllProjects();
        if(projects.isEmpty()){
            System.out.println("No Projects Available.");
        }
        else {
            for (Project project : projects) {
                System.out.println(project);
            }
        }
    }
}

