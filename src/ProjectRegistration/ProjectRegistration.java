package ProjectRegistration;

import Abstract.IEntity;
import Enumerations.ApplicationStatus;
import Enumerations.RegistrationStatus;
import ProjectApplication.ProjectApplication;

/**
 * Represents a project registration record where an officer registers to handle a BTO project.
 * Implements {@link IEntity} for standardized CSV persistence.
 */
public class ProjectRegistration implements IEntity{
    String registrationID;
    String projectID;
    String projectName;
    String officerId;
    RegistrationStatus status;
    public String getProjectID() {
        return projectID;
    }

    /**
     * Constructs a new {@code ProjectRegistration}.
     *
     * @param registrationID unique ID for the registration
     * @param projectID the ID of the project
     * @param projectName the name of the project
     * @param officerId the NRIC or ID of the officer registering
     * @param status the registration status (e.g., PENDING, APPROVED, REJECTED)
     */
    public ProjectRegistration(String registrationID, String projectID, String projectName, String officerId, RegistrationStatus status) {
        this.registrationID = registrationID;
        this.projectID = projectID;
        this.projectName = projectName;
        this.officerId = officerId;
        this.status = status;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOfficerId() {
        return officerId;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }


    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    /**
     * Converts the object to a CSV row format.
     *
     * @return a string representing the CSV row
     */
    @Override
    public String toCSVRow() {
        return String.join(",",
                registrationID,
                projectID,
                projectName,
                officerId,
                status.name());
    }

    /**
     * Constructs a {@code ProjectRegistration} from a CSV row.
     *
     * @param row the CSV row string
     * @return a {@code ProjectRegistration} object
     */
    @Override
    public IEntity fromCSVRow(String row) {
        String[] values = row.split(",", 5);
        return new ProjectRegistration(
                values[0].trim(),
                values[1].trim(),
                values[2].trim(),
                values[3].trim(),
                RegistrationStatus.valueOf(values[4].trim().toUpperCase()));
    }

    /**
     * Returns a string representation of the project registration.
     *
     * @return formatted string with registration details
     */
    @Override
    public String toString() {
        return " Registration ID: '" + registrationID+'\''+
                ", Project ID: " + projectID + '\'' +
                ", Project Name: '" + projectName + '\'' +
                ", Officer ID: " + officerId + '\'' +
                ", Status: " + status.name() +'\''
                ;
    }

    /**
     * Returns the ID used for repository operations.
     *
     * @return the registration ID
     */
    @Override
    public String getID() {
        return registrationID;
    }

    /**
     * Prints the registration details in a readable format.
     */
    public void prettyPrint() {
        System.out.println("Registration ID: " + this.getRegistrationID());
        System.out.println("Project Name: " + this.getProjectName());
        System.out.println("Officer ID: " + this.getOfficerId());
        System.out.println("Status: " + this.getStatus());
    }
}
