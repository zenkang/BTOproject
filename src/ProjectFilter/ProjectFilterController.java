package ProjectFilter;

import Applicant.Applicant;
import Enumerations.MaritalStatus;
import Project.Project;
import Project.ProjectRepository;
import Project.ProjectController;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static Utils.RepositoryGetter.*;


public class ProjectFilterController {

    public static void displayFilteredProjects(){
        ProjectFilterBoundary projectFilterBoundary = new ProjectFilterBoundary();
        ProjectFilterBoundary.displayFilteredProjects();
    }

    public static void displayFilterMenu(){
        ProjectFilterBoundary projectFilterBoundary = new ProjectFilterBoundary();
        ProjectFilterBoundary.displayFilterMenu();
    }

    public static void displayProjectForApplicant(Applicant applicant){
        ProjectFilterBoundary projectFilterBoundary = new ProjectFilterBoundary();
        projectFilterBoundary.displayProjectsForApplicant(applicant);
    }

    public static void displayProjectsCreatedByManager(String managerName){
        ProjectFilterBoundary projectFilterBoundary = new ProjectFilterBoundary();
        ProjectFilterBoundary.displayProjectsCreatedByManager(managerName);
    }

    public static List<Project> getFilteredProjects(String location, String flatType) {
        ProjectRepository repo = getProjectRepository();

        // Build a composite predicate. Start with a predicate that accepts all projects.
        Predicate<Project> compositePredicate = p -> true;

        if (location != null && !location.isEmpty()) {
            compositePredicate = compositePredicate.and(
                    p -> p.getNeighbourhood().equalsIgnoreCase(location)
            );
        }

        if (flatType != null && !flatType.isEmpty()) {
            compositePredicate = compositePredicate.and(
                    p -> p.getType1().equalsIgnoreCase(flatType) ||
                            p.getType2().equalsIgnoreCase(flatType)
            );
        }

        List<Project> filteredProjects = repo.getByFilter(compositePredicate);

        filteredProjects = new ArrayList<>(filteredProjects);
        filteredProjects.sort(Comparator.comparing(Project::getID, String.CASE_INSENSITIVE_ORDER));
        return filteredProjects;
    }

    public static List<Project> getProjectsCreatedByManager(String managerName) {
        ProjectRepository repo = getProjectRepository();
        return repo.getByFilter(project -> project.getManager().equalsIgnoreCase(managerName));
    }

    public static List<Project> getProjectsForApplicant(Applicant applicant) {
        ProjectRepository repo = getProjectRepository();
        if (applicant.getMaritalStatus() == MaritalStatus.SINGLE) {
            return repo.getByFilter(project ->
                    project.getType1().equalsIgnoreCase("2-Room") ||
                            project.getType2().equalsIgnoreCase("2-Room")
            );
        } else if (applicant.getMaritalStatus() == MaritalStatus.MARRIED) {
            return repo.getAll();
        } else {
            return repo.getAll();
        }
    }
}
