package Enquiry;

import java.util.List;
import java.util.stream.Collectors;

public class EnquiryController {
    private static final String FILE_PATH = "src/data/EnquiryList.csv";
    private static final EnquiryRepository repo = EnquiryRepository.getInstance(FILE_PATH);

    public static void loadFromCSV() {
        // Triggering getInstance already loads the data via Repository constructor
        System.out.println("Enquiries loaded: " + repo.getAll().size());
    }

    public static void saveToCSV() {
        repo.store();
        System.out.println("Enquiries saved to CSV.");
    }

    public static void addEnquiry(Enquiry e) {
        repo.create(e);
    }

    public static List<Enquiry> getAllEnquiries() {
        return repo.getAll();
    }

    public static List<Enquiry> getEnquiriesByApplicant(String applicantNric) {
        return repo.getByFilter(e -> e.getApplicantNric().equals(applicantNric));
    }

    public static List<Enquiry> getEnquiriesByProject(String projectId) {
        return repo.getByFilter(e -> e.getProjectName().equals(projectId));
    }

    public static boolean replyToEnquiry(String enquiryId, String reply) {
        Enquiry enquiry = getEnquiryById(enquiryId);
        if (enquiry != null) {
            enquiry.setStatus(reply);
            return repo.update(enquiry);
        }
        return false;
    }

    public static void updateEnquiryMessage(String enquiryId, String newMessage) {
        Enquiry enquiry = getEnquiryById(enquiryId);
        if (enquiry != null) {
            enquiry.setMessage(newMessage);
            repo.update(enquiry);
        }
    }

    public static void deleteEnquiry(String enquiryId) {
        Enquiry e = getEnquiryById(enquiryId);
        if (e != null) {
            repo.delete(e);
        }
    }

    public static Enquiry getEnquiryById(String enquiryId) {
        return repo.getByID(enquiryId);
    }

    public static void updateEnquiry(Enquiry e) {
        repo.update(e); // triggers store() in Repository
    }

}
