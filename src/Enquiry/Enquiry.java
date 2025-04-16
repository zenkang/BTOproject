package Enquiry;

import Abstract.IEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Enquiry implements IEntity {
    private String enquiryId;
    private LocalDate date;
    private String projectName;
    private String applicantNric;
    private String message;
    private String status; // reply or "Pending"

    public Enquiry(String enquiryId, LocalDate date, String projectName, String applicantNric, String message) {
        this.enquiryId = enquiryId;
        this.date = date;
        this.projectName = projectName;
        this.applicantNric = applicantNric;
        this.message = message;
        this.status = "Pending";
    }

    public Enquiry(String enquiryId, LocalDate date, String projectName, String applicantNric, String message, String status) {
        this.enquiryId = enquiryId;
        this.date = date;
        this.projectName = projectName;
        this.applicantNric = applicantNric;
        this.message = message;
        this.status = (status == null || status.isEmpty()) ? "Pending" : status;
    }

    public String getEnquiryId() { return enquiryId; }
    public LocalDate getDate() { return date; }
    public String getProjectName() { return projectName; }
    public String getApplicantNric() { return applicantNric; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }

    public void setMessage(String message) { this.message = message; }
    public void setStatus(String status) { this.status = status; }

    public boolean isReplied() {
        return status != null && !status.equalsIgnoreCase("Pending");
    }

    @Override
    public String getID() {
        return enquiryId;
    }

    @Override
    public String toCSVRow() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String safeMsg = message.replace(",", ";");
        String safeStatus = (status != null) ? status.replace(",", ";") : "Pending";
        return enquiryId + "," +
                date.format(fmt) + "," +
                projectName + "," +
                applicantNric + "," +
                safeMsg + "," +
                safeStatus;
    }

    @Override
    public IEntity fromCSVRow(String row) {
        String[] values = row.split(",", -1);
        String id = values[0];
        LocalDate date = LocalDate.parse(values[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String project = values[2];
        String nric = values[3];
        String msg = values[4].replace(";", ",");
        String status = (values.length > 5) ? values[5].replace(";", ",") : "Pending";
        return new Enquiry(id, date, project, nric, msg, status);
    }

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
