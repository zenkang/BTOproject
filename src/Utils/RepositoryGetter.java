package Utils;
import Applicant.ApplicantRepository;
import Enquiry.EnquiryRepository;
import Enquiry.ReplyRepository;
import Manager.ManagerRepository;
import Project.ProjectRepository;
import ProjectApplication.ProjectApplicationRepository;
import User.UsersRepository;

public class RepositoryGetter {
    public static ApplicantRepository getApplicantRepository(){
        return ApplicantRepository.getInstance("./src/data/ApplicantList.csv");
    }
    public static UsersRepository getUsersRepository() {
        return UsersRepository.getInstance("./src/data/User.csv");
    }
    public static ProjectRepository getProjectRepository() {
        return ProjectRepository.getInstance("./src/data/ProjectList.csv");
    }
    public static ManagerRepository getManagerRepository() {
        return ManagerRepository.getInstance("./src/data/ManagerList.csv");
    }
    public static ProjectApplicationRepository getProjectApplicationsRepository() {
        return ProjectApplicationRepository.getInstance("./src/data/ProjectApplication.csv");
    }
    public static EnquiryRepository getEnquiryRepository() {
        return EnquiryRepository.getInstance("./src/data/EnquiryList.csv");
    }
    public static ReplyRepository getReplyRepository() {
        return ReplyRepository.getInstance("./src/data/ReplyList.csv");
    }

}
