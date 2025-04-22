package Receipt;

import Enumerations.MaritalStatus;

import java.time.LocalDate;


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


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantIc() {
        return applicantIc;
    }

    public void setApplicantIc(String applicantIc) {
        this.applicantIc = applicantIc;
    }

    public int getApplicantAge() {
        return applicantAge;
    }

    public void setApplicantAge(int applicantAge) {
        this.applicantAge = applicantAge;
    }

    public MaritalStatus getApplicantMaritalStatus() {
        return applicantMaritalStatus;
    }

    public void setApplicantMaritalStatus(MaritalStatus applicantMaritalStatus) {
        this.applicantMaritalStatus = applicantMaritalStatus;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNeighbourhood() {
        return projectNeighbourhood;
    }

    public void setProjectNeighbourhood(String projectNeighbourhood) {
        this.projectNeighbourhood = projectNeighbourhood;
    }

    public String getApplicationRoomType() {
        return applicationRoomType;
    }

    public void setApplicationRoomType(String applicationRoomType) {
        this.applicationRoomType = applicationRoomType;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getDate_generated() {
        return date_generated;
    }

    public void setDate_generated(String date_generated) {
        this.date_generated = date_generated;
    }

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
