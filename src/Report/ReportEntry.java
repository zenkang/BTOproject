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

    public String getApplicantName() {
        return applicantName;
    }

    public String getNric() {
        return nric;
    }

    public int getAge() {
        return age;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getFlatType() {
        return flatType;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }



}
