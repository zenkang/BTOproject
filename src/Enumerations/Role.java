package Enumerations;
/**
 * Represents the roles assigned to users within the system.
 * <p>
 * This enumeration defines different levels of access or responsibility.
 */
public enum Role {
    /**
     * A user who submits applications or enquiries.
     */
    APPLICANT,
    /**
     * A user who reviews and processes applications or enquiries.
     */
    OFFICER,
    /**
     * A user with managerial privileges, typically overseeing officers and system operations.
     */
    MANAGER
}
