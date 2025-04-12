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
    public void displayUserProjectApplication(String applicantID) {
        ProjectApplication currentApp = ProjectApplicationController.getApplicationByApplicantID(applicantID);
        if (currentApp == null) {
            System.out.println("You have not applied for any project.");
        } else {
            System.out.println("Your current project application:");
            System.out.println(currentApp);
        }
    }
}
