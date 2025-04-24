package Officer;

import Abstract.Repository;

import Enumerations.MaritalStatus;

/**
 * OfficerRepository is a singleton class that manages persistence for Officer entities.
 * It extends the generic Repository base class, providing functionality to parse and serialize
 * Officer objects to and from CSV format.
 */
public class OfficerRepository extends Repository<Officer> {
    static OfficerRepository instance;
    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param filePath the file path for CSV storage
     */
    private OfficerRepository(String filePath) {
        super(filePath);
    }
    /**
     * Returns the singleton instance of OfficerRepository.
     * If the instance does not exist, it will be created with the specified file path.
     *
     * @param filePath the file path used to initialize the repository
     * @return the singleton instance of OfficerRepository
     */
    public static OfficerRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new OfficerRepository(filePath);
        }
        return instance;
    }

    /**
     * Converts a CSV row string into an Officer object.
     *
     * @param row a comma-separated string representing an officer
     * @return an Officer object with values parsed from the CSV row
     */
    @Override
    public Officer fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        String nric = values[1];
        return new Officer(values[0], age, status, nric);
    }
    /**
     * Returns the CSV header used when writing Officer objects to file.
     *
     * @return a string representing the CSV header line
     */
    @Override
    public String CSVHeader() {
        return "Name,NRIC,Age,Marital Status";
    }



}
