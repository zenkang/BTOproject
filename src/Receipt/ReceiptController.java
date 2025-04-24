package Receipt;

import Applicant.Applicant;
import Applicant.ApplicantController;
import Project.Project;
import Project.ProjectController;
import ProjectApplication.ProjectApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * The ReceiptController class is responsible for generating booking receipts
 * for successfully booked HDB applications.
 * It gathers data from project and applicant entities and creates a formatted text file.
 */
public class ReceiptController {

    /**
     * Generates a receipt file for a successful HDB flat booking.
     * It retrieves relevant information from the applicant, application, and project data,
     * formats the receipt, and writes it to a `.txt` file in the "receipts" directory.
     *
     * @param name        the name of the officer handling the booking
     * @param application the application object representing the booking
     */
    public static void generateReceipt(String name, ProjectApplication application) {
        Project project = ProjectController.getProjectByID(application.getProjectID());
        Applicant applicant = ApplicantController.getApplicantById(application.getApplicantID());
        if (project == null || applicant == null) {
            System.out.println("Error: Missing project or applicant data");
            return;
        }
        Receipt receipt = new Receipt(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                applicant.getName(),
                applicant.getNric(),
                applicant.getAge(),
                applicant.getMaritalStatus(),
                project.getID(),
                project.getProjectName(),
                project.getNeighbourhood(),
                application.getRoomType(),
                name,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        String receiptContent = receipt.toMessageContent();

        String directoryPath = "receipts";
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }


        String fileName = String.format("receipt_%s_%s.txt",
                applicant.getNric(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")));

        File receiptFile = new File(directory, fileName);


        try (PrintWriter writer = new PrintWriter(new FileWriter(receiptFile))) {
            writer.println(receiptContent);
            System.out.println("Receipt generated: " + receiptFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error generating receipt: " + e.getMessage());
        }

    }

}
