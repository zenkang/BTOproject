package Project;

import Applicant.Applicant;
import Enumerations.MaritalStatus;
import Manager.ManagerController;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static Utils.RepositoryGetter.getProjectRepository;


public class ProjectController {

    public static List<String> getUniqueProjectNames() {
       return getProjectRepository().getAll()
        .stream()
        .map(Project::getProjectName)
        .distinct()
        .toList();
    }

    public static ArrayList<Project> getAllProjects() {
        return getProjectRepository().getAll();
    }

    public static Project getProjectByName(String projectName) {
        ArrayList<Project> p = getProjectRepository().getAll();
        for (Project p1 : p) {
            if (p1.getProjectName().equalsIgnoreCase(projectName)) {
                return p1;
            }
        }
        return null;
    }
    public static Project getProjectByID(String projectID) {
        return getProjectRepository().getByID(projectID);
    }
    public static boolean updateProjectName(String projectID, String newProjectName) {
            ProjectRepository projectRepository = getProjectRepository();
            Project project = projectRepository.getByID(projectID);
            project.setProjectName(newProjectName);
            return projectRepository.update(project);

    }
    public static boolean updateProjectNeighbourhood(String projectID, String newProjectNeighbourhood) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectNeighbourhood(newProjectNeighbourhood);
        return projectRepository.update(project);
    }
    public static boolean updateProjectRoomType1(String projectID, String newRoomType1) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectRoomType1(newRoomType1);
        return projectRepository.update(project);
    }
    public static boolean updateProjectRoomType2(String projectID, String newRoomType2) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectRoomType2(newRoomType2);
        return projectRepository.update(project);
    }
    public static boolean updateProjectNumOfRoomType1(String projectID, int newNumOfRoomType1) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectNumOfType1(newNumOfRoomType1);
        return projectRepository.update(project);
    }
    public static boolean updateProjectNumOfRoomType2(String projectID, int newNumOfRoomType2) {
        Project project = getProjectRepository().getByID(projectID);
        project.setProjectNumOfType2(newNumOfRoomType2);
        return getProjectRepository().update(project);
    }
    public static boolean updateSellPriceOfRoomType1(String projectID, double newSellPrice){
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setSellPriceType1(newSellPrice);
        return projectRepository.update(project);
    }
    public static boolean updateSellPriceOfRoomType2(String projectID, double newSellPrice){
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setSellPriceType2(newSellPrice);
        return projectRepository.update(project);
    }
    public static boolean updateProjectApplicationOpenDate(String projectID, LocalDate newAppOpenDate) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectApplicationOpenData(newAppOpenDate);
        return projectRepository.update(project);
    }
    public static boolean updateProjectApplicationCloseDate(String projectID, LocalDate newAppCloseDate) {
        ProjectRepository projectRepository = getProjectRepository();
        Project project = projectRepository.getByID(projectID);
        project.setProjectApplicationCloseDate(newAppCloseDate);
        return projectRepository.update(project);
    }
    public static boolean updateProjectManager(String projectID, String newProjectManager) {
        Project project = getProjectRepository().getByID(projectID);
        project.setProjectManager(newProjectManager);
        return getProjectRepository().update(project);
    }
    public static boolean deleteProject(String projectID) {
        Project project = getProjectRepository().getByID(projectID);
        return getProjectRepository().delete(project);
    }
    public static boolean createProject(Project newProject){
        return getProjectRepository().create(newProject);
    }

    public static boolean createProject(String projectName,
                                        String neighbourhood,
                                        String roomType1,
                                        int noOfUnitsType1,
                                        double sellPriceType1,
                                        String roomType2,
                                        int noOfUnitsType2,
                                        double sellPriceType2,
                                        LocalDate appDateOpen,
                                        LocalDate appDateClose,
                                        String managerID,
                                        int noOfficersSlots,
                                        String[] officers,
                                        boolean visible){
        ProjectRepository repository = getProjectRepository();
        String newID = repository.generateId();
        Project newProject = new Project(newID,projectName, neighbourhood, roomType1, noOfUnitsType1, sellPriceType1, roomType2, noOfUnitsType2, sellPriceType2,
                appDateOpen, appDateClose, managerID, noOfficersSlots, officers,visible);
        return repository.create(newProject);
    }


    public static boolean checkUniqueProjectName(String projectName) {
        List<Project> p = getProjectRepository().getByFilter(project -> project.getProjectName().equalsIgnoreCase(projectName));
        return p.isEmpty();
    }

    public static boolean checkActiveProject(String managerID) {
        List<Project> p = getProjectRepository().getByFilter(project -> (project.getManagerID().equalsIgnoreCase(managerID))&&project.isVisibility());
        return p.isEmpty();
    }

    public static boolean updateProjectVisibility(Project project, boolean b) {
        project.setVisibility(b);
        return getProjectRepository().update(project);
    }

    public static List<Project> getProjectsCreatedByManager(String managerID) {
        ProjectRepository repo = getProjectRepository();
        return repo.getByFilter(project -> project.getManagerID().equalsIgnoreCase(managerID));
    }


    public static List<Project> getProjectsForApplicant(Applicant applicant) {
        ProjectRepository repo = getProjectRepository();
        List<Project> list;
        if (applicant.getMaritalStatus() == MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            list = repo.getByFilter(project ->
                    (project.getType1().equalsIgnoreCase("2-Room") ||
                            project.getType2().equalsIgnoreCase("2-Room"))
                            && project.isVisibility()
            );
        } else if (applicant.getMaritalStatus() == MaritalStatus.MARRIED && applicant.getAge() >= 21) {
            list = repo.getByFilter(project -> project.isVisibility());
        } else {
            return Collections.emptyList();
        }
        List<ProjectApplication> applications =
                ProjectApplicationController.getApplicationByApplicantID(applicant.getID());
        if(applications.isEmpty()){
            return list;
        }

        // 2) collect all the project‑IDs this applicant has already applied to
        Set<String> appliedIds = new HashSet<>();
        for (ProjectApplication app : applications) {
            appliedIds.add(app.getProjectID());
        }
        // remove all projects whose ID is in appliedIds
        return list.stream()
                .filter(p -> !appliedIds.contains(p.getID()))
                .collect(Collectors.toList());

    }

    public static List<String> getProjectIDsForApplicant(Applicant applicant) {
        List<Project> projects = getProjectsForApplicant(applicant);
        assert projects != null;
        return projects.stream()
                .map(Project::getID)
                .distinct()
                .toList();
    }

    public static List<Project> getFilteredProjects(Predicate<Project> Filter) {
        ProjectRepository repo = getProjectRepository();
        List<Project> filteredProjects;
        if(Filter == null){
            filteredProjects = repo.getAll();

        }
        else{
            filteredProjects = repo.getByFilter(Filter);
        }
        filteredProjects = new ArrayList<>(filteredProjects);
        filteredProjects.sort(Comparator.comparing(Project::getID, String.CASE_INSENSITIVE_ORDER));
        return filteredProjects;
    }

    public static List<String> getProjectIDsManagedBy(String managerID) {
        return getProjectRepository().getAll().stream()
                .filter(p -> p.getManagerID().equalsIgnoreCase(managerID))
                .map(Project::getID)
                .toList();
    }

    public static void prettyPrintApplicantProject(Applicant applicant, Project project) {
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd MMM yyyy");
        if (applicant.getMaritalStatus() == MaritalStatus.SINGLE && applicant.getAge() >= 35) {
            System.out.println("\nProject ID: "+project.getID());
            System.out.println("Project name: " + project.getProjectName());
            System.out.println("Neighbourhood: " + project.getNeighbourhood());
            if(project.getType1().equalsIgnoreCase("2-Room")){
                System.out.println("Room Type 1: "+project.getType1());
                System.out.println("Number of units for Room Type 1: "+project.getNoOfUnitsType1());
                System.out.println("Selling price of Room Type 1: "+project.getSellPriceType1());
            }
            else {
                System.out.println("Room Type 2: "+project.getType2());
                System.out.println("Number of units for Room Type 2: "+project.getNoOfUnitsType2());
                System.out.println("Selling price of Room Type 2: "+project.getSellPriceType2());
            }
            System.out.println("Application Open Date: "+project.getAppDateOpen().format(fmt1));
            System.out.println("Application Close Date: "+project.getAppDateClose().format(fmt1));
            System.out.println("Manager-in-charge: "+ ManagerController.getNameById(project.getManagerID()));

            System.out.println("------------------------");
        }
        else{
            System.out.println("\nProject ID: "+project.getID());
            System.out.println("Project name: " + project.getProjectName());
            System.out.println("Neighbourhood: " + project.getNeighbourhood());
            System.out.println("Room Type 1: "+project.getType1());
            System.out.println("Number of units for Room Type 1: "+project.getNoOfUnitsType1());
            System.out.println("Selling price of Room Type 1: "+project.getSellPriceType1());
            System.out.println("Room Type 2: "+project.getType2());
            System.out.println("Number of units for Room Type 2: "+project.getNoOfUnitsType2());
            System.out.println("Selling price of Room Type 2: "+project.getSellPriceType2());
            System.out.println("Application Open Date: "+project.getAppDateOpen().format(fmt1));
            System.out.println("Application Close Date: "+project.getAppDateClose().format(fmt1));
            System.out.println("Manager-in-charge: "+ManagerController.getNameById(project.getManagerID()));

            System.out.println("------------------------");
        }
    }

}




