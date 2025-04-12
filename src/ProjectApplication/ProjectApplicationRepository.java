package ProjectApplication;

import Abstract.Repository;

import java.util.ArrayList;

public class ProjectApplicationRepository extends Repository<ProjectApplication> {
    public ProjectApplicationRepository(String filePath) {
        super(filePath);
    }

    @Override
    public ProjectApplication fromCSVRow(String row) {
        String[] values = row.split(",", 5);
        return new ProjectApplication(
                values[0].trim(),
                values[1].trim(),
                values[2].trim(),
                values[3].trim(),
                values[4].trim()
        );
    }


    @Override
    public String CSVHeader() {
        return "Application ID,Project ID,Room Type,Applicant ID,Status";
    }

    public ArrayList<ProjectApplication> getAllProjectApplications() {
        return this.entities;
    }

    public void display(){
        for (ProjectApplication projectApplication : entities){
            System.out.println(projectApplication);
        }
    }

    public ProjectApplication getByApplicantID(String applicantID) {
        for (ProjectApplication projectApplication : entities) {
            if (projectApplication.getApplicantID().equalsIgnoreCase(applicantID)) {
                return projectApplication;
            }
        }
        return null;
    }
    public boolean deleteProjectApplicationByID(String appID) {
        ProjectApplication projectApplication = this.getByID(appID);
        if(projectApplication == null) {
            return false;
        }
        return this.delete(projectApplication);
    }
}
