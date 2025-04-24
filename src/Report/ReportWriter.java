package Report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The {@code ReportWriter} class is responsible for generating and writing
 * booking reports to a CSV file. It serializes a list of {@link ReportEntry}
 * objects into a structured format suitable for record-keeping or analysis.
 */
public class ReportWriter {

    /**
     * Writes the booking report entries to a CSV file at the specified file path.
     * If the target directory does not exist, it will be created.
     *
     * @param entries the list of {@link ReportEntry} objects to be written to the file
     * @param path the file path where the report should be saved (e.g., "reports/report.csv")
     * @throws IOException if an I/O error occurs during writing
     */
    public void writeReport(List<ReportEntry> entries, String path) throws IOException {
        // Create directory if it doesn't exist
        File directory = new File(path).getParentFile();
        if (!directory.exists()) {
            directory.mkdirs(); // Creates all necessary parent directories
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_DATE;
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            // Existing write logic
            writer.println("Name,NRIC,Age,Marital Status,Project,Flat Type,Booking Date");
            for (ReportEntry entry : entries) {
                writer.println(String.join(",", entry.getApplicantName(),
                        entry.getNric(),
                        String.valueOf(entry.getAge()),
                        entry.getMaritalStatus().name(),
                        entry.getProjectName(),
                        entry.getFlatType(),
                        entry.getBookingDate().format(dateFormatter)));
            }
        }
    }
}
