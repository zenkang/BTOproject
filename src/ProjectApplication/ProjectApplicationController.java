package ProjectApplication;

import java.util.ArrayList;

public class ProjectApplicationController {
    private static ProjectApplicationRepository retrieveProjectApplications() {
        return new ProjectApplicationRepository("./src/data/ProjectApplication.csv");
    }
    public static ArrayList<ProjectApplication> getAllProjectApplications() {
        return retrieveProjectApplications().getAllProjectApplications();
    }
    public static void displayAllProjectApplications(){
        ProjectApplicationBoundary projectApplicationBoundary = new ProjectApplicationBoundary();
        projectApplicationBoundary.displayProjectApplications();
    }

    public static ProjectApplication getApplicationByID(String appID) {
        ProjectApplicationRepository projectApplicationRepository = retrieveProjectApplications();
        return projectApplicationRepository.getByAppID(appID);
    }
}
