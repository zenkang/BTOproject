package ProjectApplication;

import Abstract.IEntity;
import Enumerations.ApplicationStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

;

/**
 * Represents an application submitted by an applicant for a BTO project.
 * Includes details such as the project, applicant, room type, current and previous status, and booking date.
 * Implements the {@code IEntity} interface to support CSV serialization and deserialization.
 */
public class ProjectApplication implements IEntity {
    private String appID;
    private String projectID;
    private String roomType;
    private String applicantID;
    private ApplicationStatus status;
    private ApplicationStatus previousStatus;
    private LocalDate book_date;
    /**
     * Constructs a {@code ProjectApplication} with the specified details.
     *
     * @param appID           the unique ID of the application
     * @param projectID       the ID of the project applied for
     * @param roomType        the type of room applied for
     * @param applicantID     the NRIC or ID of the applicant
     * @param status          the current application status
     * @param applicationStatus the previous application status
     * @param book_date       the booking date, if applicable
     */
    public ProjectApplication(String appID, String projectID, String roomType, String applicantID, ApplicationStatus status, ApplicationStatus applicationStatus, LocalDate book_date) {
        this.appID=appID;
        this.projectID=projectID;
        this.roomType=roomType;
        this.applicantID=applicantID;
        this.status=status;
        this.previousStatus=applicationStatus;
        this.book_date=book_date;
    }
    /**
     * Returns the unique application ID.
     *
     * @return the application ID
     */
    @Override
    public String getID() {
        return appID;
    }
    /**
     * Retrieves the ID of the project associated with this application.
     *
     * @return the project ID
     */
    public String getProjectID() {
        return projectID;
    }
    /**
     * Returns the room type selected in this application.
     *
     * @return the room type (e.g., "2-room", "3-room")
     */
    public String getRoomType() {
        return roomType;
    }
    /**
     * Retrieves the ID of the applicant who submitted this application.
     *
     * @return the applicant's ID
     */
    public String getApplicantID() {
        return applicantID;
    }
    /**
     * Returns the current status of the application.
     *
     * @return the application status (e.g., PENDING, SUCCESSFUL)
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Converts this application into a CSV-compatible row.
     *
     * @return a comma-separated string representing this object
     */
    @Override
    public String toCSVRow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = (book_date != null) ? book_date.format(dtf) : "N/A";
        return String.join(",",
                appID,
                projectID,
                roomType,
                applicantID,
                status.name(),
                previousStatus.name(),
                formattedDate);
    }

    /**
     * Constructs a {@code ProjectApplication} from a single CSV row.
     *
     * @param row the CSV row string
     * @return a {@code ProjectApplication} object parsed from the row
     */
    @Override
    public ProjectApplication fromCSVRow(String row) {
        String[] values = row.split(",", 7);
        LocalDate book_date = null;
        if(!values[6].trim().equals("N/A")){
            book_date = LocalDate.parse(values[6], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return new ProjectApplication(
                values[0].trim(),
                values[1].trim(),
                values[2].trim(),
                values[3].trim(),
                ApplicationStatus.valueOf(values[4].trim().toUpperCase()),
                ApplicationStatus.valueOf(values[5].trim().toUpperCase()),
                book_date
        );
    }

    /**
     * Returns a human-readable string representation of this application.
     *
     * @return formatted string of application details
     */
    @Override
    public String toString() {
        return " Application ID: '" + appID+'\''+
                ", Project ID: " + projectID + '\'' +
                ", Room Type: '" + roomType + '\'' +
                ", Applicant ID: " + applicantID + '\'' +
                ", Status: " + status.name() +'\'' +
                ", Status: " + status.name() +'\'' +
                ", Book Date: " + book_date;

    }

    /**
     * Sets the unique ID for this application.
     *
     * @param appID the application ID
     */
    public void setAppID(String appID) {
        this.appID = appID;
    }
    /**
     * Sets the ID of the project associated with this application.
     *
     * @param projectID the project ID
     */
    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }
    /**
     * Sets the room type selected in the application.
     *
     * @param roomType the selected room type (e.g., "2-room", "3-room")
     */
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    /**
     * Sets the applicant ID for this application.
     *
     * @param applicantID the ID of the applicant
     */
    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }
    /**
     * Retrieves the booking date associated with this application.
     *
     * @return the booking date, or {@code null} if not yet booked
     */

    public LocalDate getBook_date() {
        return book_date;
    }
    /**
     * Sets the booking date for the application.
     *
     * @param book_date the booking date
     */
    public void setBook_date(LocalDate book_date) {
        this.book_date = book_date;
    }
    /**
     * Updates the current status of the application.
     *
     * @param status the new application status
     */
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    /**
     * Retrieves the previous status of the application before its most recent update.
     *
     * @return the previous {@link ApplicationStatus} of the application
     */
    public ApplicationStatus getPreviousStatus() {
        return previousStatus;
    }
    /**
     * Sets the previous status of the application.
     *
     * @param previousStatus the status before the current update
     */
    public void setPreviousStatus(ApplicationStatus previousStatus) {
        this.previousStatus = previousStatus;
    }

    /**
     * Prints the application details in a user-friendly format.
     * Intended for display to the applicant.
     */
    public void prettyPrintApplicant() {
        System.out.println("\n======== Project Application ========");
        System.out.println("Application ID: " + this.getID());
        System.out.println("Project ID: " + this.getProjectID());
        System.out.println("Room Type Applied: "+this.getRoomType());
        System.out.println("Status: " + this.getStatus());
    }
}
