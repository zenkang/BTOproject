package Enquiry;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import Abstract.Repository;

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

    public static List<Enquiry> getUnrepliedEnquiriesByProjects(String managerId) {
        List<String> projectsIds = ProjectController.getProjectIDsManagedBy(managerId);
        List<Project> projects = ProjectController.getProjectsByIDs(projectsIds);
         List<String> projectNames = projects.stream()
                .map(Project::getProjectName)
                .collect(Collectors.toList());
        return RepositoryGetter.getEnquiryRepository().getByFilter(e ->
                projectNames.stream().anyMatch(
                        name -> name.equalsIgnoreCase(e.getProjectName().trim())
                ) && !e.isReplied()
        );

    }


}
