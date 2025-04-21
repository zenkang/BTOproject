package Project;

import Abstract.Repository;
import Utils.CsvUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProjectRepository extends Repository<Project>{
    private static ProjectRepository instance;
    private ProjectRepository(String filePath) {
            super(filePath);
    }

    public static ProjectRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ProjectRepository(filePath);
        }
        return instance;
    }


        @Override
        public Project fromCSVRow(String row) {
            String[] values = row.split(",");
            int noOfUnitsType1 = Integer.parseInt(values[4].trim());
            double sellPriceType1 = Double.parseDouble(values[5].trim());
            int noOfUnitsType2 = Integer.parseInt(values[7].trim());
            double sellPriceType2 = Double.parseDouble(values[8].trim());
            int noOfficersSlots = Integer.parseInt(values[12].trim());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate openDate = LocalDate.parse(CsvUtils.formatDate(values[9]), formatter);
            LocalDate closeDate = LocalDate.parse(CsvUtils.formatDate(values[10]), formatter);
            String[] officers = values[13].split(";");
            for (int i = 0; i < officers.length; i++) {
                officers[i] = officers[i].trim(); // Trim each officer ID
            }
            boolean visible = Boolean.parseBoolean(values[14].trim());
            if (LocalDate.now().isAfter(closeDate)) {
                visible = false;
            }
            return new Project(values[0],values[1], values[2],values[3],noOfUnitsType1,
                    sellPriceType1,values[6],noOfUnitsType2,sellPriceType2,
                    openDate,closeDate,values[11],noOfficersSlots,
                    officers,visible);
        }

        @Override
        public String CSVHeader() {
            return "ID,Project Name,Neighbourhood,Type 1," +
                    "Number of Units,Selling Price,Type 2," +
                    "Number of Units,Selling Price,Application Open Date," +
                    "Application Closing Date,ManagerID,Officer Slot,Officer,"+
                    "Visible";
        }


        public void display(){
            for (Project p : entities){
                System.out.println(p);
            }
        }
}


