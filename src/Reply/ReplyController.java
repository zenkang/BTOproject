package Reply;

import Enquiry.Enquiry;
import Enquiry.EnquiryController;

import Utils.RepositoryGetter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * The ReplyController class manages the creation, retrieval, and display of replies
 * to enquiries within the system. It also updates the status of related enquiries upon reply submission.
 */
public class ReplyController {


    /**
     * Adds a reply to an existing enquiry.
     * This also updates the associated enquiry's status to {@code REPLIED}.
     *
     * @param enquiryId the ID of the enquiry being replied to
     * @param userId    the ID of the officer or manager replying
     * @param content   the content of the reply message
     */
    public static void addReply(String enquiryId, String userId, String content) {
        ReplyRepository repo = RepositoryGetter.getReplyRepository();
        LocalDate today = LocalDate.now();
        String id = repo.generateId("RE");
        Reply reply = new Reply(id, enquiryId, today, userId, content);
        repo.create(reply);

        // Update the enquiry's status to REPLIED
        Enquiry enquiry = EnquiryController.getEnquiryById(enquiryId);
        if (enquiry != null) {
            enquiry.setStatus(Enumerations.EnquiryStatus.REPLIED);
            EnquiryController.updateEnquiry(enquiry);
        }
    }
    /**
     * Retrieves a list of replies made to a specific enquiry.
     *
     * @param enquiryId the ID of the enquiry
     * @return a list of replies associated with the enquiry
     */
    public static List<Reply> getRepliesByEnquiry(String enquiryId) {
        return RepositoryGetter.getReplyRepository().getByFilter(r -> r.getEnquiryId().equalsIgnoreCase(enquiryId));
    }


}
