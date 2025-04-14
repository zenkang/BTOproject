package Utils;
import Applicant.ApplicantRepository;
import Manager.ManagerRepository;
import Project.ProjectRepository;
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

}
