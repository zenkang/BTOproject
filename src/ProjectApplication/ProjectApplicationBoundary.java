package ProjectApplication;

import java.util.ArrayList;

public class ProjectApplicationBoundary {
    public void displayProjectApplications() {
    ArrayList<ProjectApplication> projectApplications = ProjectApplicationController.getAllProjectApplications();
    if (projectApplications.isEmpty()) {
        System.out.println("No Projects Available.");
    } else {
        for (ProjectApplication projectApplication : projectApplications) {
            System.out.println(projectApplication);
        }
    }
}
}
