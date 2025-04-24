package Utils;
import Applicant.ApplicantRepository;
import Enquiry.EnquiryRepository;
import Officer.OfficerRepository;
import ProjectRegistration.ProjectRegistrationRepository;
import Reply.ReplyRepository;
import Manager.ManagerRepository;
import Project.ProjectRepository;
import ProjectApplication.ProjectApplicationRepository;
import User.UsersRepository;

/**
 * Utility class for retrieving singleton instances of all repositories in the system.
 * Provides centralized access to all persistent data sources by wrapping their respective
 * getInstance() methods with default file paths.
 */
public class RepositoryGetter {

    /**
     * @return the singleton instance of ApplicantRepository
     */
    public static ApplicantRepository getApplicantRepository(){
        return ApplicantRepository.getInstance("./src/data/ApplicantList.csv");
    }

    /**
     * @return the singleton instance of UsersRepository
     */
    public static UsersRepository getUsersRepository() {
        return UsersRepository.getInstance("./src/data/User.csv");
    }

    /**
     * @return the singleton instance of ProjectRepository
     */
    public static ProjectRepository getProjectRepository() {
        return ProjectRepository.getInstance("./src/data/ProjectList.csv");
    }

    /**
     * @return the singleton instance of ManagerRepository
     */
    public static ManagerRepository getManagerRepository() {
        return ManagerRepository.getInstance("./src/data/ManagerList.csv");
    }

    /**
     * @return the singleton instance of OfficerRepository
     */
    public static OfficerRepository getOfficerRepository() {
        return OfficerRepository.getInstance("./src/data/OfficerList.csv");
    }

    /**
     * @return the singleton instance of ProjectApplicationRepository
     */
    public static ProjectApplicationRepository getProjectApplicationsRepository() {
        return ProjectApplicationRepository.getInstance("./src/data/ProjectApplication.csv");
    }

    /**
     * @return the singleton instance of EnquiryRepository
     */
    public static EnquiryRepository getEnquiryRepository() {
        return EnquiryRepository.getInstance("./src/data/EnquiryList.csv");
    }

    /**
     * @return the singleton instance of ReplyRepository
     */
    public static ReplyRepository getReplyRepository() {
        return ReplyRepository.getInstance("./src/data/ReplyList.csv");
    }

    /**
     * @return the singleton instance of ProjectRegistrationRepository
     */
    public static ProjectRegistrationRepository getProjectRegistrationRepository() {
        return ProjectRegistrationRepository.getInstance("./src/data/ProjectRegistration.csv");
    }

}
