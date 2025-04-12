package ProjectApplication;

import Abstract.IEntity;
import Project.Project;

public class ProjectApplication implements IEntity {
    private String appID;
    private String projectID;
    private String roomType;
    private String applicantID;
    private String status;

    public ProjectApplication(String appID,String projectID,String roomType,String applicantID,String status){
        this.appID=appID;
        this.projectID=projectID;
        this.roomType=roomType;
        this.applicantID=applicantID;
        this.status=status;
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
    public String getStatus() {
        return status;
    }

    @Override
    public String toCSVRow() {
        return String.join(",",
                appID,
                projectID,
                roomType,
                applicantID,
                status);
    }

    @Override
    public ProjectApplication fromCSVRow(String row) {
        String[] values = row.split(",", 5);
        return new ProjectApplication(
                values[0].trim(),
                values[1].trim(),
                values[2].trim(),
                values[3].trim(),
                values[4].trim()
        );
    }

    @Override
    public String toString() {
        return " Application ID: '" + appID+'\''+
                ", Project ID: " + projectID + '\'' +
                ", Room Type: '" + roomType + '\'' +
                ", Applicant ID: " + applicantID + '\'' +
                ", Status: " + status +'\''
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

    public void setStatus(String status) {
        this.status = status;
    }
}
