package ProjectFilter;

import Applicant.Applicant;
import Enumerations.MaritalStatus;
import Project.Project;
import Project.ProjectRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static Utils.RepositoryGetter.*;

public class ProjectFilterController {

    public static void displayFilteredProjects() {
        ProjectFilterBoundary.displayFilteredProjects();
    }

    public static void displayFilterMenu() {
        ProjectFilterBoundary.displayFilterMenu();
    }

    public static void displayProjectForApplicant(Applicant applicant) {
        new ProjectFilterBoundary().displayProjectsForApplicant(applicant);
    }

    public static void displayProjectsCreatedByManager(String managerName) {
        ProjectFilterBoundary.displayProjectsCreatedByManager(managerName);
    }

    public static List<Project> getFilteredProjects(String location, String flatType) {
        ProjectRepository repo = getProjectRepository();

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

        List<Project> filteredProjects = new ArrayList<>(repo.getByFilter(compositePredicate));
        filteredProjects.sort(Comparator.comparing(Project::getID, String.CASE_INSENSITIVE_ORDER));
        return filteredProjects;
    }

    public static List<Project> getProjectsCreatedByManager(String managerName) {
        return getProjectRepository().getByFilter(
                project -> project.getManager().equalsIgnoreCase(managerName)
        );
    }

    public static List<Project> getProjectsForApplicant(Applicant applicant) {
        ProjectRepository repo = getProjectRepository();
        if (applicant.getMaritalStatus() == MaritalStatus.SINGLE) {
            return repo.getByFilter(project ->
                    "2-Room".equalsIgnoreCase(project.getType1()) ||
                            "2-Room".equalsIgnoreCase(project.getType2())
            );
        } else {
            return null;
        }
    }
}
