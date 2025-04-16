package Reply;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import Enquiry.Enquiry;
import Enquiry.EnquiryController;

public class ReplyController {
    private static final String FILE_PATH = "src/data/ReplyList.csv";
    private static final ReplyRepository repo = ReplyRepository.getInstance(FILE_PATH);

    public static void addReply(String enquiryId, String userId, String content) {
        String replyId = UUID.randomUUID().toString().substring(0, 8);
        LocalDate today = LocalDate.now();
        Reply reply = new Reply(replyId, enquiryId, today, userId, content);
        repo.create(reply);

        Enquiry enquiry = EnquiryController.getEnquiryById(enquiryId);
        if (enquiry != null) {
            enquiry.setStatus(content); // reply content becomes status
            EnquiryController.updateEnquiry(enquiry);
        }
    }

    public static List<Reply> getRepliesByEnquiry(String enquiryId) {
        return repo.getByFilter(r -> r.getEnquiryId().equalsIgnoreCase(enquiryId));
    }

    public static List<Reply> getRepliesByResponder(String userId) {
        return repo.getByFilter(r -> r.getOfficerOrManagerId().equalsIgnoreCase(userId));
    }

    public static void displayAllReplies() {
        repo.display();
    }
}
