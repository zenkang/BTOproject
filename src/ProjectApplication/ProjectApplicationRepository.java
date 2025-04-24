package ProjectApplication;

import Abstract.Repository;
import Enumerations.ApplicationStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 * Repository class for managing {@link ProjectApplication} entities.
 * Handles operations such as parsing CSV rows, generating headers, and retrieving or deleting applications.
 */
public class ProjectApplicationRepository extends Repository<ProjectApplication> {
    private static ProjectApplicationRepository instance;
    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param filePath the file path where project application data is stored
     */
    private ProjectApplicationRepository(String filePath) {
        super(filePath);
    }
    /**
     * Returns the singleton instance of the repository.
     *
     * @param filePath the CSV file path to load or create repository from
     * @return the singleton {@code ProjectApplicationRepository} instance
     */
    public static ProjectApplicationRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ProjectApplicationRepository(filePath);
        }
        return instance;
    }
    /**
     * Converts a CSV row into a {@link ProjectApplication} object.
     *
     * @param row a line from the CSV file
     * @return the corresponding {@code ProjectApplication} object
     */
    @Override
    public ProjectApplication fromCSVRow(String row) {
        String[] values = row.split(",", 7);
        LocalDate book_date = null;
        if(!values[6].trim().equals("N/A")){
            book_date = LocalDate.parse(values[6], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return new ProjectApplication(
                values[0].trim(),
                values[1].trim(),
                values[2].trim(),
                values[3].trim(),
                ApplicationStatus.valueOf(values[4].trim().toUpperCase()),
                ApplicationStatus.valueOf(values[5].trim().toUpperCase()),
                book_date);
    }

    /**
     * Returns the CSV header for this repository.
     *
     * @return the header string used when saving to CSV
     */
    @Override
    public String CSVHeader() {
        return "Application ID,Project ID,Room Type,Applicant ID,Status,Previous Status,Book Date";
    }

    /**
     * Returns all stored {@link ProjectApplication} objects.
     *
     * @return list of all project applications
     */
    public ArrayList<ProjectApplication> getAllProjectApplications() {
        return this.entities;
    }
    /**
     * Displays all project applications to the console.
     */
    public void display(){
        for (ProjectApplication projectApplication : entities){
            System.out.println(projectApplication);
        }
    }

}
