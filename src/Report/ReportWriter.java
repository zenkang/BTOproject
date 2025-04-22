package Report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportWriter {
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
