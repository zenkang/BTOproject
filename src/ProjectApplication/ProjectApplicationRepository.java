package ProjectApplication;

import Abstract.Repository;
import Project.Project;

import java.util.ArrayList;

public class ProjectApplicationRepository extends Repository<ProjectApplication> {
    public ProjectApplicationRepository(String filePath) {
        super(filePath);
    }

    @Override
    public ProjectApplication fromCSVRow(String row) {
        String[] values = row.split(",",5);
        return new ProjectApplication(values[0],values[1], values[2],values[3],values[4]);

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

    public ProjectApplication getByAppID(String appID) {
        for (ProjectApplication projectApplication : entities) {
            if (projectApplication.getID().equalsIgnoreCase(appID)) {
                return projectApplication;
            }
        }
        return null;
    }

}
