package User;

import Abstract.IEntity;
import Enumerations.Role;
import Utils.CsvUtils;
import Utils.PasswordHasher;

/**
 * Represents a user in the system with authentication credentials and a specific role.
 * Implements IEntity to support CSV serialization and entity identification.
 * This class is commonly used for login and access control logic.
 */
public class User implements IEntity {
    private String nric;
    private String password;
    private final Role role;

    /**
     * Constructs a new User with the specified NRIC, password, and role.
     *
     * @param nric the user's NRIC, used as a unique identifier
     * @param password the user's password
     * @param role the role assigned to the user (e.g., APPLICANT, MANAGER, OFFICER)
     */
    public User(String nric, String password, Role role) {
        this.nric = nric;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the NRIC of the user.
     *
     * @return the NRIC
     */
    public String getNric() {
        return nric;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the role of the user.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns a string representation of the User.
     *
     * @return string containing NRIC, password, and role
     */
    @Override
    public String toString() {
        return "User [nric=" + nric + ", password=" + password + ", role=" + role.toString() + "]";
    }

    /**
     * Converts the User to a CSV-formatted string.
     *
     * @return CSV string: NRIC,Password,Role
     */
    @Override
    public String toCSVRow() {
        String normalisedRole = CsvUtils.capitalizeFirstLetter(this.role.toString());
        return nric+","+password+","+normalisedRole;
    }

    /**
     * Parses a CSV row string to construct a new User object.
     *
     * @param row a comma-separated string representing a user
     * @return the constructed User object
     */
    public User fromCSVRow(String row) {
        String[] values = row.split(",");
        Role role = Role.valueOf(values[2].toUpperCase());
        return new User(values[0], values[1], role);
    }

    /**
     * Gets the unique identifier (NRIC) of the user.
     *
     * @return the NRIC as the user ID
     */
    @Override
    public String getID() {
        return nric;
    }

    /**
     * Sets the NRIC of the user.
     *
     * @param nric the new NRIC
     */
    public void setNric(String nric) {
        this.nric = nric;
    }
}
