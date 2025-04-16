package Enquiry;

import Abstract.IEntity;

public class Enquiry implements IEntity{
    private String enquiryId;
    private String applicantNric;
    private String projectName;
    private String message;
    private String reply;

    public Enquiry(String enquiryId, String applicantNric, String projectId, String message) {
        this.enquiryId = enquiryId;
        this.applicantNric = applicantNric;
        this.projectName = projectId;
        this.message = message;
        this.reply = "Pending reply"; // Default value
    }

    // Constructor used when loading from CSV with optional reply
    public Enquiry(String enquiryId, String applicantNric, String projectId, String message, String reply) {
        this.enquiryId = enquiryId;
        this.applicantNric = applicantNric;
        this.projectName = projectId;
        this.message = message;
        this.reply = (reply == null || reply.isEmpty()) ? "Pending reply" : reply;
    }

    public String getEnquiryId() {
        return enquiryId;
    }

    public String getApplicantNric() {
        return applicantNric;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String getID() {
        return enquiryId;
    }

    @Override
    public String toCSVRow() {
        // Escape commas inside message/reply with semicolons
        String safeMessage = message != null ? message.replace(",", ";") : "";
        String safeReply = reply != null ? reply.replace(",", ";") : "";
        return enquiryId + "," + applicantNric + "," + projectName + "," + safeMessage + "," + safeReply;
    }

    @Override
    public IEntity fromCSVRow(String row) {
        String[] values = row.split(",", -1); // -1 keeps empty strings
        String id = values[0];
        String nric = values[1];
        String project = values[2];
        String msg = values[3].replace(";", ",");
        String reply = values.length > 4 ? values[4].replace(";", ",") : null;
        return new Enquiry(id, nric, project, msg, reply);
    }

    @Override
    public String toString() {
        return "Enquiry from " + applicantNric + " (Project: " + projectName + "): " + message +
                "\nReply: " + reply;
    }
}
