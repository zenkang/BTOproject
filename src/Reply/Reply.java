package Reply;

import Abstract.IEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a reply to an enquiry made by either an officer or a manager.
 * Each reply is associated with an enquiry ID and includes content, date, and author ID.
 */
public class Reply implements IEntity {
    private String replyId;
    private String enquiryId;
    private LocalDate date;
    private String officerOrManagerId;
    private String replyContent;
    /**
     * Constructs a Reply object.
     *
     * @param replyId              the unique identifier for the reply
     * @param enquiryId            the ID of the enquiry this reply is associated with
     * @param date                 the date the reply was made
     * @param officerOrManagerId   the ID of the officer or manager who made the reply
     * @param replyContent         the content of the reply
     */
    public Reply(String replyId, String enquiryId, LocalDate date, String officerOrManagerId, String replyContent) {
        this.replyId = replyId;
        this.enquiryId = enquiryId;
        this.date = date;
        this.officerOrManagerId = officerOrManagerId;
        this.replyContent = replyContent;
    }
    /** @return the reply's unique ID */
    public String getReplyId() { return replyId; }
    /** @return the ID of the associated enquiry */
    public String getEnquiryId() { return enquiryId; }
    /** @return the date the reply was made */
    public LocalDate getDate() { return date; }
    /** @return the ID of the officer or manager who replied */
    public String getOfficerOrManagerId() { return officerOrManagerId; }
    /** @return the content of the reply */
    public String getReplyContent() { return replyContent; }
    /**
     * Retrieves the unique identifier (ID) for this reply.
     *
     * @return the ID of the reply
     */
    @Override
    public String getID() {
        return replyId;
    }

    /**
     * Converts this reply to a CSV-formatted string.
     *
     * @return a comma-separated representation of this reply
     */
    @Override
    public String toCSVRow() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return replyId + "," +
                enquiryId + "," +
                date.format(fmt) + "," +
                officerOrManagerId + "," +
                replyContent.replace(",", ";");
    }

    /**
     * Parses a CSV-formatted string and creates a new Reply object.
     *
     * @param row the CSV-formatted reply row
     * @return a new Reply object
     */
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

    /**
     * Provides a multi-line string representation of the reply.
     *
     * @return a string showing reply details
     */
    @Override
    public String toString() {
        return "ReplyID: " + replyId +
                "\nEnquiryID: " + enquiryId +
                "\nDate: " + date +
                "\nResponder: " + officerOrManagerId +
                "\nReply: " + replyContent;
    }

    /**
     * Prints the reply in a structured, user-friendly format.
     */
    public void printPrettyReply() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Reply ID: " + this.getReplyId());
        System.out.println("Enquiry ID: " + this.getEnquiryId());
        System.out.println("Date: " + this.getDate().format(dateFormatter));
        System.out.println("Officer / Manager ID: "+this.getOfficerOrManagerId());
        System.out.println("Reply: "+this.getReplyContent());
        System.out.println("----------------------\n");
    }
}
