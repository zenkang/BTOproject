package Enquiry;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import Abstract.Repository;

import Project.Project;
import Project.ProjectController;
import Utils.RepositoryGetter;

import static Utils.RepositoryGetter.getEnquiryRepository;
/**
 * The EnquiryController class handles logic for managing enquiries.
 * It supports adding, deleting, updating, and filtering enquiries based on
 * project ownership, applicant NRIC, and enquiry status.
 */
public class EnquiryController {

    /**
     * Retrieves all enquiries from the repository.
     *
     * @return a list of all {@link Enquiry} objects
     */
    public static List<Enquiry> getAllEnquiries() {
        return RepositoryGetter.getEnquiryRepository().getAll();
    }

    /**
     * Creates and stores a new enquiry for a specific project and applicant.
     *
     * @param today        the current date of enquiry submission
     * @param projectName  the name of the project the enquiry is about
     * @param nric         the NRIC of the applicant
     * @param message      the enquiry message content
     */
    public static void addEnquiry(LocalDate today, String projectName, String nric, String message) {
        Repository repository = getEnquiryRepository();
        String id = repository.generateId("EN");
        Enquiry e = new Enquiry(id, today, projectName, nric, message);
        RepositoryGetter.getEnquiryRepository().create(e);
    }

    /**
     * Retrieves all enquiries submitted by a specific applicant.
     *
     * @param applicantNric the NRIC of the applicant
     * @return a list of {@link Enquiry} objects submitted by the applicant
     */
    public static List<Enquiry> getEnquiriesByApplicant(String applicantNric) {
        return RepositoryGetter.getEnquiryRepository().getByFilter(e -> e.getApplicantNric().equals(applicantNric));
    }

    /**
     * Deletes an enquiry by its ID if it exists.
     *
     * @param enquiryId the ID of the enquiry to be deleted
     */
    public static void deleteEnquiry(String enquiryId) {
        Enquiry e = getEnquiryById(enquiryId);
        if (e != null) {
            RepositoryGetter.getEnquiryRepository().delete(e);
        }
    }

    /**
     * Retrieves an enquiry by its unique ID.
     *
     * @param enquiryId the enquiry ID
     * @return the corresponding Enquiry object, or null if not found
     */
    public static Enquiry getEnquiryById(String enquiryId) {
        return RepositoryGetter.getEnquiryRepository().getByID(enquiryId);
    }

    /**
     * Updates an existing enquiry in the repository.
     *
     * @param e the enquiry object with updated fields
     */
    public static void updateEnquiry(Enquiry e) {
        RepositoryGetter.getEnquiryRepository().update(e); // triggers store() in Repository
    }

    /**
     * Retrieves all unreplied enquiries for projects managed by the specified manager.
     *
     * @param managerId the ID of the manager
     * @return a list of unreplied enquiries for the manager's projects
     */
    public static List<Enquiry> getUnrepliedEnquiriesByProjects(String managerId) {
        List<String> projectsIds = ProjectController.getProjectIDsManagedBy(managerId);
        List<Project> projects = ProjectController.getProjectsByIDs(projectsIds);
         List<String> projectNames = projects.stream()
                .map(Project::getProjectName)
                .toList();
        return RepositoryGetter.getEnquiryRepository().getByFilter(e ->
                projectNames.stream().anyMatch(
                        name -> name.equalsIgnoreCase(e.getProjectName().trim())
                ) && !e.isReplied()
        );

    }


    /**
     * Retrieves all unreplied enquiries for projects handled by the given officer.
     *
     * @param id the officer ID
     * @return a list of unreplied enquiries for the officer's handled projects
     */
    public static List<Enquiry> getEnquiriesForHandledProject(String id) {
        List<String> projectNames = ProjectController.getProjectNamesHandledByOfficer(id);
        return RepositoryGetter.getEnquiryRepository().getByFilter(
                e -> projectNames.stream().anyMatch(
                        name -> name.equalsIgnoreCase(e.getProjectName().trim())
                )&& !e.isReplied()
        );
    }

    /**
     * Retrieves all enquiries (replied and unreplied) for projects handled by the officer.
     *
     * @param officerId the ID of the officer
     * @return a list of all enquiries associated with the officer's projects
     */
    public static List<Enquiry> getAllEnquiriesForHandledProjects(String officerId) {
        List<String> projectNames = ProjectController.getProjectNamesHandledByOfficer(officerId);
        return RepositoryGetter.getEnquiryRepository().getByFilter(
                e -> projectNames.stream().anyMatch(
                        name -> name.equalsIgnoreCase(e.getProjectName().trim())
                )
        );
    }
}