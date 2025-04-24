package Manager;

import Abstract.IEntity;
import Abstract.IUserProfile;
import Enumerations.MaritalStatus;
import User.User;
import Utils.CsvUtils;

/**
 * Represents a Manager in the system, implementing both IEntity and IUserProfile interfaces.
 * This class encapsulates the profile data of a manager including name, NRIC, age, and marital status,
 * and includes logic for CSV serialization and deserialization.
 */
public class Manager implements IEntity, IUserProfile {
    private User userProfile;
    private String name;
    private int age;
    private MaritalStatus maritalStatus;
    private String Nric;

    /**
     * Constructs a Manager with basic profile attributes.
     *
     * @param value the name of the manager
     * @param age the age of the manager
     * @param status the marital status of the manager
     * @param nric the NRIC of the manager
     */
    public Manager(String value, int age, MaritalStatus status, String nric) {
        this.name = value;
        this.age = age;
        this.maritalStatus = status;
        this.Nric = nric;
    }

    /**
     * Returns a string representation of the Manager.
     *
     * @return formatted string with manager profile data
     */
    @Override
    public String toString() {
        return "Manager [name=" + name + ", NRIC=" + Nric
                + ", Age=" + age + ", MaritalStatus=" + maritalStatus.toString() + "]";
    }

    /**
     * Converts the manager's data into a CSV row format.
     *
     * @return a comma-separated string representing the manager
     */
    @Override
    public String toCSVRow() {
        String normalisedStatus = CsvUtils.capitalizeFirstLetter(this.maritalStatus.toString());
        return name + "," + Nric + "," + age + "," + normalisedStatus;
    }

    /**
     * Reconstructs a Manager object from a CSV row.
     *
     * @param row a comma-separated string containing manager data
     * @return a Manager instance parsed from the row
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
     * Returns the unique ID (NRIC) of the manager.
     *
     * @return the manager's NRIC
     */
    @Override
    public String getID() {
        return Nric;
    }

    /**
     * Returns the underlying User profile associated with this manager.
     *
     * @return the User object
     */
    public User getUserProfile() {
        return userProfile;
    }

    /**
     * Sets the associated User profile for this manager.
     *
     * @param userProfile the User object to associate
     */
    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Returns the manager's NRIC.
     *
     * @return NRIC
     */
    @Override
    public String getNric() {return Nric;
    }

    /**
     * Updates the NRIC for the manager in both the local and user profile object.
     *
     * @param nric the new NRIC
     */
    public void setNric(String nric) {
        this.userProfile.setNric(nric);
        this.Nric = nric;
    }

    /**
     * Retrieves the password from the associated user profile.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return this.userProfile.getPassword();
    }

    /**
     * Sets a new password in the user profile.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.userProfile.setPassword(password);
    }

    /**
     * Returns the name of the manager.
     *
     * @return name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the manager.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the age of the manager.
     *
     * @return age
     */
    @Override
    public int getAge() {
        return age;
    }

    /**
     * Updates the manager's age.
     *
     * @param age the new age value
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Retrieves the marital status of the manager.
     *
     * @return marital status
     */
    @Override
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the marital status of the manager.
     *
     * @param maritalStatus the new marital status
     */
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
