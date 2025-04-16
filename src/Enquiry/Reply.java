package Enquiry;

import Abstract.IEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reply implements IEntity {
    private String replyId;
    private String enquiryId;
    private LocalDate date;
    private String officerOrManagerId;
    private String replyContent;

    public Reply(String replyId, String enquiryId, LocalDate date, String officerOrManagerId, String replyContent) {
        this.replyId = replyId;
        this.enquiryId = enquiryId;
        this.date = date;
        this.officerOrManagerId = officerOrManagerId;
        this.replyContent = replyContent;
    }

    public String getReplyId() { return replyId; }
    public String getEnquiryId() { return enquiryId; }
    public LocalDate getDate() { return date; }
    public String getOfficerOrManagerId() { return officerOrManagerId; }
    public String getReplyContent() { return replyContent; }

    @Override
    public String getID() {
        return replyId;
    }

    @Override
    public String toCSVRow() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return replyId + "," +
                enquiryId + "," +
                date.format(fmt) + "," +
                officerOrManagerId + "," +
                replyContent.replace(",", ";");
    }

    @Override
    public IEntity fromCSVRow(String row) {
        String[] fields = row.split(",", -1);
        String replyId = fields[0];
        String enquiryId = fields[1];
        LocalDate date = LocalDate.parse(fields[2], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String userId = fields[3];
        String content = fields[4].replace(";", ",");
        return new Reply(replyId, enquiryId, date, userId, content);
    }

    @Override
    public String toString() {
        return "ReplyID: " + replyId +
                "\nEnquiryID: " + enquiryId +
                "\nDate: " + date +
                "\nResponder: " + officerOrManagerId +
                "\nReply: " + replyContent;
    }
}
