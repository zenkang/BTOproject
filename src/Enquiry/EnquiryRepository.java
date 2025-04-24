package Enquiry;

import Abstract.Repository;

import java.util.List;

/**
 * The {EnquiryRepository class provides a singleton implementation for managing
 * persistent storage and retrieval of Enquiry objects from a CSV file.
 * It extends the generic Repository class with Enquiry-specific parsing logic.
 */
public class EnquiryRepository extends Repository<Enquiry> {
    private static EnquiryRepository instance;

    /**
     * Private constructor to initialize the repository with the file path.
     * Use {@link #getInstance(String)} to retrieve the singleton instance.
     *
     * @param filePath the path to the CSV file used for persistence
     */
    private EnquiryRepository(String filePath) {
        super(filePath);
    }

    /**
     * Returns the singleton instance of the {@code EnquiryRepository}.
     * If it does not exist, it creates a new one with the provided file path.
     *
     * @param filePath the path to the CSV file used for storage
     * @return the singleton instance of the repository
     */
    public static EnquiryRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new EnquiryRepository(filePath);
        }
        return instance;
    }

    /**
     * Converts a CSV row into an {@link Enquiry} object.
     *
     * @param row the CSV-formatted string representing one enquiry
     * @return an {@code Enquiry} object created from the CSV row
     */
    @Override
    public Enquiry fromCSVRow(String row) {
        return (Enquiry) new Enquiry("", null, "", "", "").fromCSVRow(row);
    }

    /**
     * Returns the CSV header used for storing enquiry records.
     *
     * @return the header string for the CSV file
     */
    @Override
    public String CSVHeader() {
        return "EnquiryID,Date,ProjectName,ApplicantID,Question,Status";
    }







}
