package Reply;

import Enquiry.Enquiry;
import Enquiry.EnquiryController;

import Utils.RepositoryGetter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ReplyController {



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

    public static List<Reply> getRepliesByEnquiry(String enquiryId) {
        return RepositoryGetter.getReplyRepository().getByFilter(r -> r.getEnquiryId().equals(enquiryId));
    }

    public static List<Reply> getRepliesByResponder(String userId) {
        return RepositoryGetter.getReplyRepository().getByFilter(r -> r.getOfficerOrManagerId().equalsIgnoreCase(userId));
    }

    public static void displayAllReplies() {
        RepositoryGetter.getReplyRepository().display();
    }
}
