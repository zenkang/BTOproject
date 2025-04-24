package Report;

import Enumerations.MaritalStatus;

import java.time.LocalDate;

/**
 * Represents a single entry in a BTO booking report.
 * Each entry contains information about an applicant and the project they successfully booked.
 */
public class ReportEntry {
    private String applicantName;
    private String nric;
    private int age;
    private MaritalStatus maritalStatus;
    private String projectName;
    private String flatType;
    private LocalDate bookingDate;
    /**
     * Constructs a {@code ReportEntry} with the specified applicant and project details.
     *
     * @param applicantName the name of the applicant
     * @param nric the NRIC of the applicant
     * @param age the age of the applicant
     * @param maritalStatus the marital status of the applicant
     * @param projectName the name of the booked project
     * @param flatType the flat type booked (e.g., "2-Room", "3-Room")
     * @param bookingDate the date on which the flat was booked
     */
    public ReportEntry(String applicantName, String nric, int age, MaritalStatus maritalStatus, String projectName, String flatType, LocalDate bookingDate) {
        this.applicantName = applicantName;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.projectName = projectName;
        this.flatType = flatType;
        this.bookingDate = bookingDate;
    }

    /**
     * Retrieves the name of the applicant.
     *
     * @return the name of the applicant
     */
    public String getApplicantName() {
        return applicantName;
    }
    /**
     * Retrieves the NRIC (National Registration Identity Card) of the applicant.
     *
     * @return the NRIC of the applicant
     */
    public String getNric() {
        return nric;
    }
    /**
     * Retrieves the age of the applicant.
     *
     * @return the age of the applicant
     */
    public int getAge() {
        return age;
    }
    /**
     * Retrieves the marital status of the applicant.
     *
     * @return the marital status of the applicant
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }
    /**
     * Retrieves the project name associated with the applicant's registration.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }
    /**
     * Retrieves the flat type for the applicant's registration.
     *
     * @return the flat type
     */
    public String getFlatType() {
        return flatType;
    }
    /**
     * Retrieves the booking date of the applicant's registration.
     *
     * @return the booking date
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }



}
