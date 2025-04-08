package Enquiry;

import java.util.ArrayList;

public class EnquiryController {
	private static ArrayList<Enquiry> enquiries = new ArrayList<>();

    public static void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    public static ArrayList<Enquiry> getEnquiriesByApplicant(String applicantNric) {
        ArrayList<Enquiry> result = new ArrayList<>();
        for (Enquiry e : enquiries) {
            if (e.getApplicantNric().equals(applicantNric)) {
                result.add(e);
            }
        }
        return result;
    }

    public static ArrayList<Enquiry> getEnquiriesByProject(String projectId) {
        ArrayList<Enquiry> result = new ArrayList<>();
        for (Enquiry e : enquiries) {
            if (e.getProjectName().equals(projectId)) {
                result.add(e);
            }
        }
        return result;
    }

    public static boolean replyToEnquiry(String enquiryId, String reply) {
        for (Enquiry e : enquiries) {
            if (e.getEnquiryId().equals(enquiryId)) {
                e.setReply(reply);
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Enquiry> getAllEnquiries() {
        return enquiries;
    }
    
    public static void updateEnquiryMessage(String enquiryId, String newMessage) {
        Enquiry enquiry = getEnquiryById(enquiryId);
        if (enquiry != null) {
            enquiry.setMessage(newMessage);
        }
    }
    
    public static void deleteEnquiry(String enquiryId) {
        enquiries.removeIf(e -> e.getEnquiryId().equals(enquiryId));
    }
    
    public static Enquiry getEnquiryById(String enquiryId) {
        for (Enquiry e : enquiries) {
            if (e.getEnquiryId().equals(enquiryId)) {
                return e;
            }
        }
        return null;
    }

}
