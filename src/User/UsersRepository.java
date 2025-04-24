package User;
import Abstract.Repository;
import Enumerations.Role;

import java.util.ArrayList;

/**
 * Singleton repository class for managing User entities.
 * Handles loading from and saving to CSV files, parsing user credentials and roles.
 * Provides global access to user data within the system.
 */
public class UsersRepository extends Repository<User> {

    private static UsersRepository instance;

    /**
     * Private constructor to enforce the Singleton pattern.
     * Initializes the repository with the specified CSV file path.
     *
     * @param filePath the path to the CSV file containing user data
     */
    private UsersRepository(String filePath) {
        super(filePath);
    }

    /**
     * Provides access to the singleton instance of UsersRepository.
     * If the instance doesn't exist, it creates one using the provided file path.
     *
     * @param filePath the path to the CSV file to initialize the repository if needed
     * @return the singleton instance of UsersRepository
     */
    public static UsersRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new UsersRepository(filePath);
        }
        return instance;
    }

    /**
     * Parses a CSV-formatted row into a User object.
     * Assumes the format: NRIC,PASSWORD,ROLE
     *
     * @param row the CSV string representing a user
     * @return a new User object created from the row
     */
    @Override
    public User fromCSVRow(String row) {
        String[] values = row.split(",");
        Role role = Role.valueOf(values[2].toUpperCase());
        return new User(values[0], values[1], role);
    }

    /**
     * Returns the header for the User CSV file.
     *
     * @return the CSV header string: "NRIC,PASSWORD,ROLE"
     */
    @Override
    public String CSVHeader() {
        return "NRIC,PASSWORD,ROLE";
    }

    /**
     * Retrieves all users currently stored in memory.
     *
     * @return an ArrayList of all User objects
     */
    public ArrayList<User> getAllUsers() {
        return this.entities;
    }

    /**
     * Displays all users in the repository by printing their string representation.
     */
    public void display(){
        for (User u : entities){
            System.out.println(u);
        }
    }
}
