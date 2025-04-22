package Report;

import Applicant.Applicant;
import Applicant.ApplicantController;
import Enumerations.ApplicationStatus;
import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;
import ProjectApplication.ProjectApplicationController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ReportController {

    public static void generateReport(String managerId,
                                      ReportCriteria criteria,
                                      String outputPath) throws IOException {

        List<Project> managedProjects = ProjectController.getProjectsCreatedByManager(managerId);


        List<ReportEntry> entries = processApplications(managedProjects, criteria);

        // 3. Generate output
        new ReportWriter().writeReport(entries, outputPath);
    }
    private static List<ReportEntry> processApplications(List<Project> projects,
                                                         ReportCriteria criteria) {
        return projects.stream()
                .flatMap(p -> ProjectApplicationController.getApplicationsByProjectID(p.getID()).stream())
                .filter(app -> matchesCriteria(app, criteria))
                .map(app -> toReportEntry(app))
                .collect(Collectors.toList());
    }
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
