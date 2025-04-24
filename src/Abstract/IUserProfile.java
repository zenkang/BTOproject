package Abstract;

import Enumerations.MaritalStatus;
/**
 * Interface representing a user profile in the system.
 * Provides essential personal and authentication-related methods
 * that any user (e.g., applicant, officer, manager) should implement.
 */
public interface IUserProfile {

    /**
     * Retrieves the full name of the user.
     *
     * @return the user's name
     */
    public String getName();

    /**
     * Retrieves the NRIC of the user.
     *
     * @return the user's NRIC
     */
    public String getNric();

    /**
     * Retrieves the age of the user.
     *
     * @return the user's age
     */
    public int getAge();

    /**
     * Retrieves the marital status of the user.
     *
     * @return the user's marital status
     */
    public MaritalStatus getMaritalStatus();


    /**
     * Retrieves the user's password.
     * Typically used for authentication or validation purposes.
     *
     * @return the user's password
     */
    public String getPassword();
}
