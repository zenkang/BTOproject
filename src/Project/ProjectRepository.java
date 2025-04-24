package Project;

import Abstract.Repository;
import Utils.CsvUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProjectRepository handles the persistence and retrieval of Project entities
 * from a CSV file. It implements the Singleton pattern to ensure a single instance
 * interacts with the project data storage.
 */
public class ProjectRepository extends Repository<Project>{
    private static ProjectRepository instance;

    /**
     * Private constructor to enforce singleton usage.
     *
     * @param filePath the file path to the project CSV file
     */
    private ProjectRepository(String filePath) {
            super(filePath);
    }


    /**
     * Retrieves the singleton instance of ProjectRepository.
     * Initializes and stores the repository data on first access to ensure it's up-to-date.
     *
     * @param filePath the file path to the project CSV file
     * @return the singleton instance of {@code ProjectRepository}
     */
    public static ProjectRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ProjectRepository(filePath);
            instance.store();//update to make sure project.csv is the latest
        }
        return instance;
    }

        /**
         * Converts a CSV row into a {@code Project} object.
         * Parses unit counts, prices, officer list, and visibility from the string row.
         *
         * @param row a single line from the CSV representing a Project
         * @return the constructed {@code Project} object
         */
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
            List<String> officers = new ArrayList<String>(Arrays.stream(values[13].split(";")).map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList()));
            boolean visible = Boolean.parseBoolean(values[14].trim());
            return new Project(values[0],values[1], values[2],values[3],noOfUnitsType1,
                    sellPriceType1,values[6],noOfUnitsType2,sellPriceType2,
                    openDate,closeDate,values[11],noOfficersSlots,
                    officers,visible);
        }

        /**
         * Provides the header row for the CSV file, representing all Project fields.
         *
         * @return a comma-separated header string
         */
        @Override
        public String CSVHeader() {
            return "ID,Project Name,Neighbourhood,Type 1," +
                    "Number of Units,Selling Price,Type 2," +
                    "Number of Units,Selling Price,Application Open Date," +
                    "Application Closing Date,ManagerID,Officer Slot,Officer,"+
                    "Visible";
        }

        /**
         * Displays all project entries currently loaded in memory.
         */
        public void display(){
            for (Project p : entities){
                System.out.println(p);
            }
        }
}


