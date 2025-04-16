package Enquiry;

import Abstract.IEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Enumerations.EnquiryStatus;

public class Enquiry implements IEntity {
    private String enquiryId;
    private LocalDate date;
    private String projectName;
    private String applicantNric;
    private String message;
    private EnquiryStatus status;

    // Used when creating new enquiry directly (default to PENDING)
    public Enquiry(String enquiryId, LocalDate date, String projectName, String applicantNric, String message) {
        this.enquiryId = enquiryId;
        this.date = date;
        this.projectName = projectName;
        this.applicantNric = applicantNric;
        this.message = message;
        this.status = EnquiryStatus.PENDING;
    }

    // Used when loading from CSV or raw String (converts to enum safely)
    public Enquiry(String enquiryId, LocalDate date, String projectName, String applicantNric, String message, String status) {
        this.enquiryId = enquiryId;
        this.date = date;
        this.projectName = projectName;
        this.applicantNric = applicantNric;
        this.message = message;

        if (status == null || status.isBlank()) {
            this.status = EnquiryStatus.PENDING;
        } else {
            try {
                this.status = EnquiryStatus.valueOf(status.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                this.status = EnquiryStatus.PENDING;
            }
        }
    }

    // Used internally when EnquiryStatus is already available
    public Enquiry(String enquiryId, LocalDate date, String projectName, String applicantNric, String message, EnquiryStatus status) {
        this.enquiryId = enquiryId;
        this.date = date;
        this.projectName = projectName;
        this.applicantNric = applicantNric;
        this.message = message;
        this.status = (status != null) ? status : EnquiryStatus.PENDING;
    }

    public String getEnquiryId() { return enquiryId; }
    public LocalDate getDate() { return date; }
    public String getProjectName() { return projectName; }
    public String getApplicantNric() { return applicantNric; }
    public String getMessage() { return message; }
    public EnquiryStatus getStatus() { return status; }

    public void setMessage(String message) { this.message = message; }
    public void setStatus(EnquiryStatus status) { this.status = status; }

    public boolean isReplied() {
        return status == EnquiryStatus.REPLIED;
    }

    @Override
    public String getID() {
        return enquiryId;
    }

    @Override
    public String toCSVRow() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String safeMsg = message.replace(",", ";");
        return enquiryId + "," + date.format(fmt) + "," + projectName + "," + applicantNric + "," + safeMsg + "," + status.name();
    }

    @Override
    public IEntity fromCSVRow(String row) {
        String[] values = row.split(",", -1);
        String id = values[0];
        LocalDate date = LocalDate.parse(values[1], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String project = values[2];
        String nric = values[3];
        String msg = values[4].replace(";", ",");
        EnquiryStatus status = (values.length > 5)
                ? EnquiryStatus.valueOf(values[5].trim().toUpperCase())
                : EnquiryStatus.PENDING;

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
