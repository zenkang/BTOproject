package Project;

import Abstract.Repository;
import Applicant.Applicant;
import Enumerations.Role;
import Project.Project;

import java.util.ArrayList;

import static Applicant.ApplicantController.getApplicantRepository;

public class ProjectRepository extends Repository<Project>{
    public ProjectRepository(String filePath) {
            super(filePath);
        }

        @Override
        public Project fromCSVRow(String row) {
            String[] values = row.split(",");
            int noOfUnitsType1 = Integer.parseInt(values[4].trim());
            int sellPriceType1 = Integer.parseInt(values[5].trim());
            int noOfUnitsType2 = Integer.parseInt(values[7].trim());
            int sellPriceType2 = Integer.parseInt(values[8].trim());
            int noOfficersSlots = Integer.parseInt(values[12].trim());
            String[] officer = values[13].split(",");
            boolean visible = Boolean.parseBoolean(values[14].trim());
            return new Project(values[0],values[1], values[2],values[3],noOfUnitsType1,
                    sellPriceType1,values[6],noOfUnitsType2,sellPriceType2,
                    values[9],values[10],values[11],noOfficersSlots,
                    officer,visible);

        }

        @Override
        public String CSVHeader() {
            return "ID,Project Name,Neighbourhood,Type 1," +
                    "Number of Units,Selling Price,Type 2," +
                    "Number of Units,Selling Price,Application Open Date" +
                    "Application Closing Date,Manager,Officer Slot,Officer"+
                    "Visible";
        }

        public ArrayList<Project> getAllProjects() {
            return this.entities;
        }


        public void display(){
            for (Project p : entities){
                System.out.println(p);
            }
        }
    public Project getByProjectName(String projectName) {
        for (Project p : entities) {
            if (p.getProjectName().equalsIgnoreCase(projectName)) {
                return p;
            }
        }
        return null;
    }
    public Project getByProjectID(String projectID) {
        for (Project p : entities) {
            if (p.getID().equalsIgnoreCase(projectID)) {
                return p;
            }
        }
        return null;
    }
    public boolean deleteProjectByID(String projectID) {
        Project project = this.getByID(projectID);
        if(project == null) {
            return false;
        }
        return this.delete(project);
    }

    public boolean checkValidId(int projectId) {
        for (Project p : entities) {
            if(p.getID().equalsIgnoreCase(String.valueOf(projectId))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkUniqueProjectName(String projectName) {
        for (Project p : entities) {
            if(p.getProjectName().equalsIgnoreCase(projectName)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkActiveProject(String managerName) {
        for (Project p : entities) {
            if(p.getManager().equalsIgnoreCase(managerName) && p.isVisibility()) {
                return false;
            }
        }
        return true;
    }


}


