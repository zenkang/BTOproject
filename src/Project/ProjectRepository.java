package Project;

import Abstract.Repository;
import Enumerations.Role;
import Project.Project;

import java.util.ArrayList;

public class ProjectRepository extends Repository<Project>{
        public ProjectRepository(String filePath) {
            super(filePath);
        }

        @Override
        public Project fromCSVRow(String row) {
            String[] values = row.split(",");
            int noOfUnitsType1 = Integer.parseInt(values[3].trim());
            int sellPriceType1 = Integer.parseInt(values[4]);
            int noOfUnitsType2 = Integer.parseInt(values[6]);
            int sellPriceType2 = Integer.parseInt(values[7]);
            int noOfficersSlots = Integer.parseInt(values[11]);
            String[] officer = values[12].split(",");
            return new Project(values[0], values[1],values[2],noOfUnitsType1,
                    sellPriceType1,values[5],noOfUnitsType2,sellPriceType2,
                    values[8],values[9],values[10],noOfficersSlots,
                    officer);

        }

        @Override
        public String CSVHeader() {
            return "Project Name,Neighbourhood,Type 1," +
                    "Number of Units,Selling Price,Type 2," +
                    "Number of Units,Selling Price,Application Open Date" +
                    "Application Closing Date,Manager,Officer Slot,Officer";
        }

        public ArrayList<Project> getAllProjects() {
            return this.entities;
        }


        public void display(){
            for (Project p : entities){
                System.out.println(p);
            }
        }
    }


