package ProjectApplication;

import Abstract.IEntity;
import Enumerations.ApplicationStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

;


public class ProjectApplication implements IEntity {
    private String appID;
    private String projectID;
    private String roomType;
    private String applicantID;
    private ApplicationStatus status;
    private ApplicationStatus previousStatus;


    private LocalDate book_date;

    public ProjectApplication(String appID, String projectID, String roomType, String applicantID, ApplicationStatus status, ApplicationStatus applicationStatus, LocalDate book_date) {
        this.appID=appID;
        this.projectID=projectID;
        this.roomType=roomType;
        this.applicantID=applicantID;
        this.status=status;
        this.previousStatus=applicationStatus;
        this.book_date=book_date;
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


    public LocalDate getBook_date() {
        return book_date;
    }

    public void setBook_date(LocalDate book_date) {
        this.book_date = book_date;
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

    public void prettyPrintApplicant() {
        System.out.println("\n======== Project Application ========");
        System.out.println("Application ID: " + this.getID());
        System.out.println("Project ID: " + this.getProjectID());
        System.out.println("Room Type Applied: "+this.getRoomType());
        System.out.println("Status: " + this.getStatus());
    }
}
