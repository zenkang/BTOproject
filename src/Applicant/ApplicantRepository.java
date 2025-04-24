package Applicant;

import Abstract.Repository;
import Enumerations.MaritalStatus;
import Enumerations.Role;
import Utils.CsvUtils;
import User.User;

/**
 * Concrete implementation of Repository for managing Applicant entities.
 * Handles loading from and saving to CSV files, and parses Applicant data accordingly.
 */
public class ApplicantRepository extends Repository<Applicant> {
    static ApplicantRepository instance;
    private ApplicantRepository(String filePath) {
        super(filePath);
    }

    /**
     * Provides the singleton instance of the ApplicantRepository.
     *
     * @param filePath the path to the CSV file to be used if creating a new instance
     * @return the singleton instance of ApplicantRepository
     */
    public static ApplicantRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ApplicantRepository(filePath);
        }
        return instance;
    }

    /**
     * Converts a CSV row string into an Applicant object.
     * Assumes the format: Name,NRIC,Age,Marital Status
     *
     * @param row the CSV-formatted string
     * @return the Applicant object constructed from the row data
     */
    @Override
    public Applicant fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        String nric = values[1];
        return new Applicant(values[0], age, status, nric);
    }

    /**
     * Returns the CSV header for the Applicant CSV file.
     *
     * @return the CSV header string: "Name,NRIC,Age,Marital Status"
     */
    @Override
    public String CSVHeader() {
        return "Name,NRIC,Age,Marital Status";
    }





}