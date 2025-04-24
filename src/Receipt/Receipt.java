package Receipt;

import Enumerations.MaritalStatus;

import java.time.LocalDate;


/**
 * Represents a flat booking receipt in the HDB system.
 * It stores details about the applicant, project, and the officer involved.
 */
public class Receipt {
    private String date;
    private String applicantName;
    private String applicantIc;
    private int applicantAge;
    private MaritalStatus applicantMaritalStatus;
    private String projectId;
    private String projectName;
    private String projectNeighbourhood;
    private String applicationRoomType;
    private String officerName;
    private String date_generated;

    /**
     * Constructs a new Receipt instance with all relevant booking information.
     *
     * @param date                   the date of booking
     * @param applicantName          the name of the applicant
     * @param applicantIc            the NRIC of the applicant
     * @param applicantAge           the age of the applicant
     * @param applicantMaritalStatus the marital status of the applicant
     * @param projectId              the ID of the booked project
     * @param projectName            the name of the project
     * @param projectNeighbourhood   the neighborhood where the project is located
     * @param applicationRoomType    the room type applied for
     * @param officerName            the name of the officer who handled the booking
     * @param date_generated         the system-generated date of the receipt
     */
    public Receipt(String date, String applicantName, String applicantIc, int applicantAge, MaritalStatus applicantMaritalStatus, String projectId, String projectName, String projectNeighbourhood, String applicationRoomType, String officerName, String date_generated) {
        this.date = date;
        this.applicantName = applicantName;
        this.applicantIc = applicantIc;
        this.applicantAge = applicantAge;
        this.applicantMaritalStatus = applicantMaritalStatus;
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectNeighbourhood = projectNeighbourhood;
        this.applicationRoomType = applicationRoomType;
        this.officerName = officerName;
        this.date_generated = date_generated;
    }

    /** @return the booking date */
    public String getDate() {
        return date;
    }
    /** @param date the booking date to set */
    public void setDate(String date) {
        this.date = date;
    }
    /** @return the name of the applicant */
    public String getApplicantName() {
        return applicantName;
    }
    /** @param applicantName the applicant's name to set */
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }
    /** @return the NRIC of the applicant */
    public String getApplicantIc() {
        return applicantIc;
    }
    /** @param applicantIc the NRIC to set */
    public void setApplicantIc(String applicantIc) {
        this.applicantIc = applicantIc;
    }
    /** @return the age of the applicant */
    public int getApplicantAge() {
        return applicantAge;
    }
    /** @param applicantAge the age to set */
    public void setApplicantAge(int applicantAge) {
        this.applicantAge = applicantAge;
    }
    /** @return the marital status of the applicant */
    public MaritalStatus getApplicantMaritalStatus() {
        return applicantMaritalStatus;
    }
    /** @param applicantMaritalStatus the marital status to set */
    public void setApplicantMaritalStatus(MaritalStatus applicantMaritalStatus) {
        this.applicantMaritalStatus = applicantMaritalStatus;
    }
    /** @return the ID of the booked project */
    public String getProjectId() {
        return projectId;
    }
    /** @param projectId the project ID to set */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    /** @return the name of the project */
    public String getProjectName() {
        return projectName;
    }
    /** @param projectName the project name to set */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    /** @return the neighborhood of the project */
    public String getProjectNeighbourhood() {
        return projectNeighbourhood;
    }
    /** @param projectNeighbourhood the neighborhood to set */
    public void setProjectNeighbourhood(String projectNeighbourhood) {
        this.projectNeighbourhood = projectNeighbourhood;
    }
    /** @return the room type selected in the application */
    public String getApplicationRoomType() {
        return applicationRoomType;
    }
    /** @param applicationRoomType the room type to set */
    public void setApplicationRoomType(String applicationRoomType) {
        this.applicationRoomType = applicationRoomType;
    }
    /** @return the name of the officer handling the booking */
    public String getOfficerName() {
        return officerName;
    }
    /** @param officerName the officer's name to set */
    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }
    /** @return the date the receipt was generated */
    public String getDate_generated() {
        return date_generated;
    }
    /** @param date_generated the generated date to set */
    public void setDate_generated(String date_generated) {
        this.date_generated = date_generated;
    }
    /**
     * Generates a formatted string representing the contents of this receipt.
     *
     * @return the receipt formatted as a message string
     */
    public String toMessageContent(){
        return String.format(
                "=== HDB FLAT BOOKING RECEIPT ===\n" +
                        "Date: %s\n\n" +
                        "Applicant Details:\n" +
                        "Name: %s\n" +
                        "NRIC: %s\n" +
                        "Age: %d\n" +
                        "Marital Status: %s\n\n" +
                        "Project Details:\n" +
                        "Project ID: %s\n" +
                        "Name: %s\n" +
                        "Neighborhood: %s\n" +
                        "Flat Type: %s\n\n" +
                        "Officer Details:\n" +
                        "Name: %s\n" +
                        "Generated on: %s\n" +
                        "================================",
                this.date,
                this.applicantName,
                this.applicantIc,
                this.applicantAge,
                this.applicantMaritalStatus,
                this.projectId,
                this.projectName,
                this.projectNeighbourhood,
                this.applicationRoomType,
                this.officerName,
                this.date_generated
        );

    }
}
