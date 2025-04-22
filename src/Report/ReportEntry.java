package Report;

import Enumerations.MaritalStatus;

import java.time.LocalDate;

public class ReportEntry {
    private String applicantName;
    private String nric;
    private int age;
    private MaritalStatus maritalStatus;
    private String projectName;
    private String flatType;
    private LocalDate bookingDate;

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
