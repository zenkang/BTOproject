package Report;

import Applicant.Applicant;
import Applicant.ApplicantController;
import Enumerations.ApplicationStatus;
import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;

import java.time.LocalDate;

public class ReportController {



    private boolean matchesCriteria(ProjectApplication app, ReportCriteria criteria) {
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


}
