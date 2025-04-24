package ProjectRegistration;

import Abstract.Repository;

import Enumerations.RegistrationStatus;



/**
 * Repository class for managing {@link ProjectRegistration} entities.
 * Handles data loading and saving between CSV files and {@code ProjectRegistration} objects.
 * Extends the generic {@code Repository} class for reusable CRUD operations.
 */
public class ProjectRegistrationRepository extends Repository<ProjectRegistration> {
    private static ProjectRegistrationRepository instance;
    /**
     * Constructs a {@code ProjectRegistrationRepository} with the specified file path.
     *
     * @param filePath the path to the CSV file used for storage
     */
    public ProjectRegistrationRepository(String filePath) {
        super(filePath);
    }
    /**
     * Singleton access method for obtaining the repository instance.
     * Ensures only one instance is used throughout the application.
     *
     * @param filePath the path to the CSV file used for storage
     * @return the singleton {@code ProjectRegistrationRepository} instance
     */
    public static ProjectRegistrationRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ProjectRegistrationRepository(filePath);
        }
        return instance;
    }

    /**
     * Converts a CSV row string into a {@code ProjectRegistration} object.
     * Returns {@code null} if the row is malformed or missing critical fields.
     *
     * @param row a single CSV line representing a {@code ProjectRegistration}
     * @return a {@code ProjectRegistration} object, or {@code null} if invalid
     */
    @Override
    public ProjectRegistration fromCSVRow(String row) {

        if (row == null || row.trim().isEmpty()) {
            return null;
        }

        String[] values = row.split(",", -1);
        if (values.length < 5) {
            return null;
        }

        for (int i = 0; i < 5; i++) {
            values[i] = values[i].trim();
        }

        if (values[0].isEmpty() || values[1].isEmpty() || values[2].isEmpty()) {
            return null;
        }

        return new ProjectRegistration(
                values[0],
                values[1],
                values[2],
                values[3],
                RegistrationStatus.valueOf(values[4].toUpperCase())
        );
        }

    /**
     * Returns the header string used for the CSV file.
     *
     * @return a comma-separated header string
     */
    @Override
    public String CSVHeader() {
        return "Registration ID,Project ID,Project Name,Officer ID,Status";
    }
    }





