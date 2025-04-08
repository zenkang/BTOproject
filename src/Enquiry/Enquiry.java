package Enquiry;

public class Enquiry {
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
        this.reply = null;
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
    public String toString() {
        return "Enquiry from " + applicantNric + " (Project: " + projectName + "): " + message +
               (reply != null ? "\nReply: " + reply : "\nStatus: Pending reply");
    }
}
