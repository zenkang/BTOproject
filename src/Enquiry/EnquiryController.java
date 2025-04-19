package Enquiry;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import Abstract.Repository;
import Enumerations.EnquiryStatus;
import Project.Project;
import Project.ProjectController;
import Utils.RepositoryGetter;

import static Utils.RepositoryGetter.getEnquiryRepository;
public class EnquiryController {





    public static List<Enquiry> getAllEnquiries() {
        return RepositoryGetter.getEnquiryRepository().getAll();
    }

    public static void addEnquiry(LocalDate today, String projectName, String nric, String message) {
        Repository repository = getEnquiryRepository();
        String id = repository.generateId("EN");
        Enquiry e = new Enquiry(id, today, projectName, nric, message);
        RepositoryGetter.getEnquiryRepository().create(e);
    }



    public static List<Enquiry> getEnquiriesByApplicant(String applicantNric) {
        return RepositoryGetter.getEnquiryRepository().getByFilter(e -> e.getApplicantNric().equals(applicantNric));
    }

    public static List<Enquiry> getEnquiriesByProject(String projectId) {
        return RepositoryGetter.getEnquiryRepository().getByFilter(e -> e.getProjectName().equals(projectId));
    }

    public static boolean replyToEnquiry(String enquiryId, String reply) {
        Enquiry enquiry = getEnquiryById(enquiryId);
        if (enquiry != null) {
            enquiry.setStatus(EnquiryStatus.REPLIED);
            return RepositoryGetter.getEnquiryRepository().update(enquiry);
        }
        return false;
    }

    public static void updateEnquiryMessage(String enquiryId, String newMessage) {
        Enquiry enquiry = getEnquiryById(enquiryId);
        if (enquiry != null) {
            enquiry.setMessage(newMessage);
            RepositoryGetter.getEnquiryRepository().update(enquiry);
        }
    }

    public static void deleteEnquiry(String enquiryId) {
        Enquiry e = getEnquiryById(enquiryId);
        if (e != null) {
            RepositoryGetter.getEnquiryRepository().delete(e);
        }
    }

    public static Enquiry getEnquiryById(String enquiryId) {
        return RepositoryGetter.getEnquiryRepository().getByID(enquiryId);
    }

    public static void updateEnquiry(Enquiry e) {
        RepositoryGetter.getEnquiryRepository().update(e); // triggers store() in Repository
    }

    public static List<Enquiry> getUnrepliedEnquiriesByProjects(String manager_name) {
        List<Project> myProjects = ProjectController.getProjectsManagedBy(manager_name);
        List<String> myProjectNames = myProjects.stream()
                .map(Project::getProjectName)
                .collect(Collectors.toList());
        return RepositoryGetter.getEnquiryRepository().getUnrepliedByProjectNames(myProjectNames);
    }


}
