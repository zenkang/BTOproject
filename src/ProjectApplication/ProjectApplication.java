package ProjectApplication;

import Abstract.IEntity;
import Enumerations.ApplicationStatus;


public class ProjectApplication implements IEntity {
    private String appID;
    private String projectID;
    private String roomType;
    private String applicantID;
    private ApplicationStatus status;
    private ApplicationStatus previousStatus;

    public ProjectApplication(String appID, String projectID, String roomType, String applicantID, ApplicationStatus status, ApplicationStatus applicationStatus){
        this.appID=appID;
        this.projectID=projectID;
        this.roomType=roomType;
        this.applicantID=applicantID;
        this.status=status;
        this.previousStatus=applicationStatus;
    }
    @Override
    public String getID() {
        return appID;
    }
    public String getProjectID() {
        return projectID;
    }
    public String getRoomType() {
        return roomType;
    }
    public String getApplicantID() {
        return applicantID;
    }
    public ApplicationStatus getStatus() {
        return status;
    }

    @Override
    public String toCSVRow() {
        return String.join(",",
                appID,
                projectID,
                roomType,
                applicantID,
                status.name(),
                previousStatus.name());
    }

    @Override
    public ProjectApplication fromCSVRow(String row) {
        String[] values = row.split(",", 6);
        return new ProjectApplication(
                values[0].trim(),
                values[1].trim(),
                values[2].trim(),
                values[3].trim(),
                ApplicationStatus.valueOf(values[4].trim().toUpperCase()),
                ApplicationStatus.valueOf(values[5].trim().toUpperCase())
        );
    }

    @Override
    public String toString() {
        return " Application ID: '" + appID+'\''+
                ", Project ID: " + projectID + '\'' +
                ", Room Type: '" + roomType + '\'' +
                ", Applicant ID: " + applicantID + '\'' +
                ", Status: " + status.name() +'\'' +
                ", Status: " + status.name() +'\''
                ;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setApplicantID(String applicantID) {
        this.applicantID = applicantID;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    public ApplicationStatus getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(ApplicationStatus previousStatus) {
        this.previousStatus = previousStatus;
    }
}
