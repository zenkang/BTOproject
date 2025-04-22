package ProjectRegistration;

import Abstract.IEntity;
import Enumerations.ApplicationStatus;
import Enumerations.RegistrationStatus;
import ProjectApplication.ProjectApplication;

public class ProjectRegistration implements IEntity{
    String registrationID;
    String projectID;
    String projectName;
    String officerId;
    RegistrationStatus status;
    public String getProjectID() {
        return projectID;
    }

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
    @Override
    public String toCSVRow() {
        return String.join(",",
                registrationID,
                projectID,
                projectName,
                officerId,
                status.name());
    }

    @Override
    public IEntity fromCSVRow(String row) {
        String[] values = row.split(",", 5);
        return new ProjectApplication(
                values[0].trim(),
                values[1].trim(),
                values[2].trim(),
                values[3].trim(),
                ApplicationStatus.valueOf(values[4].trim().toUpperCase()),
                ApplicationStatus.valueOf(values[5].trim().toUpperCase()));
    }
    @Override
    public String toString() {
        return " Registration ID: '" + registrationID+'\''+
                ", Project ID: " + projectID + '\'' +
                ", Project Name: '" + projectName + '\'' +
                ", Officer ID: " + officerId + '\'' +
                ", Status: " + status.name() +'\''
                ;
    }

    @Override
    public String getID() {
        return registrationID;
    }

    public void prettyPrint() {
        System.out.println("Registration ID: " + this.getRegistrationID());
        System.out.println("Project Name: " + this.getProjectName());
        System.out.println("Officer ID: " + this.getOfficerId());
        System.out.println("Status: " + this.getStatus());
    }
}
