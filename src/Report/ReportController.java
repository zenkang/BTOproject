package Report;

import Applicant.Applicant;
import Applicant.ApplicantController;
import Enumerations.ApplicationStatus;
import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code ReportController} class provides functionalities to generate
 * filtered reports of booked project applications based on a manager's managed projects
 * and a set of user-defined filtering criteria.
 */
public class ReportController {

    /**
     * Generates a report for a manager based on given filtering criteria and writes the result to a file.
     *
     * @param managerId  the ID of the manager for whom the report is generated
     * @param criteria   the {@link ReportCriteria} used to filter applications
     * @param outputPath the file path where the report should be written
     * @return {@code true} if the report contains entries and was successfully written; {@code false} otherwise
     * @throws IOException if an I/O error occurs during report writing
     */
    public static boolean generateReport(String managerId,
                                         ReportCriteria criteria,
                                         String outputPath) throws IOException {

        List<Project> managedProjects = ProjectController.getProjectsCreatedByManager(managerId);


        List<ReportEntry> entries = processApplications(managedProjects, criteria);
        if(!entries.isEmpty()){
            new ReportWriter().writeReport(entries, outputPath);
            return true;
        }

        return false;
    }
    /**
     * Processes applications for the provided projects and returns report entries that match the criteria.
     *
     * @param projects the list of projects to include in the report
     * @param criteria the filtering criteria
     * @return a list of {@link ReportEntry} objects matching the criteria
     */
    private static List<ReportEntry> processApplications(List<Project> projects,
                                                         ReportCriteria criteria) {
        return projects.stream()
                .flatMap(p -> ProjectApplicationController.getApplicationsByProjectID(p.getID()).stream())
                .filter(app -> matchesCriteria(app, criteria) && app.getStatus().equals(ApplicationStatus.BOOKED))
                .map(app -> toReportEntry(app))
                .collect(Collectors.toList());
    }
    /**
     * Checks whether a given application matches the specified filtering criteria.
     *
     * @param app      the project application to evaluate
     * @param criteria the filtering criteria
     * @return {@code true} if the application matches all specified criteria; {@code false} otherwise
     */
    private static boolean matchesCriteria(ProjectApplication app, ReportCriteria criteria) {
        Applicant applicant = ApplicantController.getApplicantById(app.getApplicantID());
        Project project = ProjectController.getProjectByID(app.getProjectID());

        return (criteria.getMaritalStatus() == null ||
                applicant.getMaritalStatus() == criteria.getMaritalStatus())
                && (criteria.getFlatType() == null ||
                app.getRoomType().equalsIgnoreCase(criteria.getFlatType()))
                && (criteria.getMinAge() == null ||
                applicant.getAge() >= criteria.getMinAge())
                && (criteria.getMaxAge() == null ||
                applicant.getAge() <= criteria.getMaxAge())
                && (criteria.getNeighborhood() == null ||
                project.getNeighbourhood().equalsIgnoreCase(criteria.getNeighborhood()));
    }
    /**
     * Converts a {@link ProjectApplication} into a {@link ReportEntry} object for report generation.
     *
     * @param app the project application to convert
     * @return the corresponding {@link ReportEntry}
     */
    private static ReportEntry toReportEntry(ProjectApplication app) {
        Applicant applicant = ApplicantController.getApplicantById(app.getApplicantID());
        Project project = ProjectController.getProjectByID(app.getProjectID());

        return new ReportEntry(
                applicant.getName(),
                applicant.getNric(),
                applicant.getAge(),
                applicant.getMaritalStatus(),
                project.getProjectName(),
                app.getRoomType(),
                app.getBook_date()
        );
    }


}
