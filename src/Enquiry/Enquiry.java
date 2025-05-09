package Enquiry;

import Abstract.IEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Enumerations.EnquiryStatus;

/**
 * The Enquiry class represents an enquiry submitted by an applicant
 * regarding a BTO project. Each enquiry includes metadata such as the date,
 * project name, applicant NRIC, message, and status (e.g., PENDING or REPLIED).
 */
public class Enquiry implements IEntity {
    private String enquiryId;
    private LocalDate date;
    private String projectName;
    private String applicantNric;
    private String message;
    private EnquiryStatus status;

    /**
     * Constructs a new enquiry with default status set to {@code PENDING}.
     *
     * @param enquiryId     the unique ID of the enquiry
     * @param date          the date the enquiry was submitted
     * @param projectName   the name of the project the enquiry is about
     * @param applicantNric the NRIC of the applicant submitting the enquiry
     * @param message       the enquiry message content
     */
    public Enquiry(String enquiryId, LocalDate date, String projectName, String applicantNric, String message) {
        this.enquiryId = enquiryId;
        this.date = date;
        this.projectName = projectName;
        this.applicantNric = applicantNric;
        this.message = message;
        this.status = EnquiryStatus.PENDING;
    }



    /**
     * Constructs an enquiry with an explicitly provided status.
     *
     * @param enquiryId     the enquiry ID
     * @param date          the date of the enquiry
     * @param projectName   the project name
     * @param applicantNric the applicant's NRIC
     * @param message       the enquiry message
     * @param status        the enquiry status
     */
    public Enquiry(String enquiryId, LocalDate date, String projectName, String applicantNric, String message, EnquiryStatus status) {
        this.enquiryId = enquiryId;
        this.date = date;
        this.projectName = projectName;
        this.applicantNric = applicantNric;
        this.message = message;
        this.status = (status != null) ? status : EnquiryStatus.PENDING;
    }

    /**
     * Returns the unique ID of the enquiry.
     *
     * @return the enquiry ID
     */
    public String getEnquiryId() { return enquiryId; }

    /**
     * Returns the date when the enquiry was made.
     *
     * @return the enquiry date
     */
    public LocalDate getDate() { return date; }

    /**
     * Returns the name of the project associated with the enquiry.
     *
     * @return the project name
     */
    public String getProjectName() { return projectName; }

    /**
     * Returns the NRIC of the applicant who made the enquiry.
     *
     * @return the applicant's NRIC
     */
    public String getApplicantNric() { return applicantNric; }

    /**
     * Returns the message or content of the enquiry.
     *
     * @return the enquiry message
     */
    public String getMessage() { return message; }

    /**
     * Returns the current status of the enquiry.
     *
     * @return the enquiry status
     */
    public EnquiryStatus getStatus() { return status; }

    /**
     * Sets the message or content of the enquiry.
     *
     * @param message the message to set for the enquiry
     */
    public void setMessage(String message) { this.message = message; }

    /**
     * Sets the status of the enquiry.
     *
     * @param status the status to assign to the enquiry
     */
    public void setStatus(EnquiryStatus status) { this.status = status; }

    /**
     * Checks if the enquiry has been replied to.
     *
     * @return true if the enquiry status is {@code REPLIED}, false otherwise
     */
    public boolean isReplied() {
        return status == EnquiryStatus.REPLIED;
    }

    /**
     * Returns the enquiry ID.
     *
     * @return the enquiry ID
     */
    @Override
    public String getID() {
        return enquiryId;
    }

    /**
     * Converts the enquiry to a CSV-compatible string.
     * Commas in the message are replaced with semicolons to preserve format.
     *
     * @return the CSV row representation of the enquiry
     */
    @Override
    public String toCSVRow() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String safeMsg = message.replace(",", ";");
        return enquiryId + "," + date.format(fmt) + "," + projectName + "," + applicantNric + "," + safeMsg + "," + status.name();
    }

    /**
     * Parses a CSV row and returns an {@code Enquiry} object.
     *
     * @param row the CSV-formatted row
     * @return a new {@code Enquiry} instance
     */
    @Override
    public IEntity fromCSVRow(String row) {
        String[] values = row.split(",", -1);
        String id = values[0];
        LocalDate date = LocalDate.parse(values[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String project = values[2];
        String nric = values[3];
        String msg = values[4].replace(";", ",");
        EnquiryStatus status = (values.length > 5)
                ? EnquiryStatus.valueOf(values[5].trim().toUpperCase())
                : EnquiryStatus.PENDING;

        return new Enquiry(id, date, project, nric, msg, status);
    }

    /**
     * Returns a string representation of the enquiry, formatted for display.
     *
     * @return the formatted enquiry details
     */
    @Override
    public String toString() {
        return "Enquiry ID: " + enquiryId +
                "\nDate: " + date +
                "\nProject: " + projectName +
                "\nFrom: " + applicantNric +
                "\nQuestion: " + message +
                "\nStatus: " + status + "\n";
    }
}
