package Manager;

import Abstract.Repository;
import Enumerations.MaritalStatus;
import Enumerations.Role;
import User.User;

/**
 * The ManagerRepository class handles persistence and retrieval of Manager entities
 * from a CSV file. It is implemented as a singleton and extends the generic Repository class.
 * It parses CSV rows into Manager objects and provides CSV header formatting.
 */
public class ManagerRepository extends Repository<Manager> {

    /**
     * Singleton repository class for managing Manager entities.
     */
    private static ManagerRepository instance;

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param filePath the path to the CSV file used for persistence
     */
    private ManagerRepository(String filePath) {
        super(filePath);
    }

    /**
     * Retrieves the singleton instance of ManagerRepository.
     * If the instance does not exist, it will be created with the provided file path.
     *
     * @param filePath the path to the CSV file
     * @return the singleton instance of ManagerRepository
     */
    public static ManagerRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ManagerRepository(filePath);
        }
        return instance;
    }

    /**
     * Converts a CSV row into a Manager object.
     * Assumes the format: Name,NRIC,Age,Marital Status
     *
     * @param row a comma-separated string representing a Manager
     * @return the Manager object represented by the CSV row
     */
    @Override
    public Manager fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        String nric = values[1];
        return new Manager(values[0], age, status, nric);
    }

    /**
     * Returns the CSV header used for exporting Manager data.
     *
     * @return a string representing the CSV header
     */
    @Override
    public String CSVHeader() {
         return "Name,NRIC,Age,Marital Status";
    }
}
