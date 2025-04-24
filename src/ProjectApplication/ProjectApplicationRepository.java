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

    /**
     * Retrieves a {@link ProjectApplication} by applicant ID.
     *
     * @param applicantID the NRIC of the applicant
     * @return the matching {@code ProjectApplication} or {@code null} if not found
     */
    public ProjectApplication getByApplicantID(String applicantID) {
        for (ProjectApplication projectApplication : entities) {
            if (projectApplication.getApplicantID().equalsIgnoreCase(applicantID)) {
                return projectApplication;
            }
        }
        return null;
    }

    /**
     * Deletes a project application by its application ID.
     *
     * @param appID the ID of the application to delete
     * @return {@code true} if deletion was successful, {@code false} otherwise
     */
    public boolean deleteProjectApplicationByID(String appID) {
        ProjectApplication projectApplication = this.getByID(appID);
        if(projectApplication == null) {
            return false;
        }
        return this.delete(projectApplication);
    }
}
