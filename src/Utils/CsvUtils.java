package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for handling CSV-related formatting operations.
 * Includes methods for string capitalization, date formatting, and validation helpers.
 */
public class CsvUtils {

    /**
     * Capitalizes only the first letter of the given string.
     * If the input is null or empty, it returns the input unchanged.
     *
     * @param input the string to capitalize
     * @return a version of the string with only the first letter in uppercase and the rest in lowercase
     */
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**
     * Formats a LocalDate object into a string using dd/MM/yyyy format.
     *
     * @param d the LocalDate to format
     * @return the formatted date string
     */
    public static String getFmtDate(LocalDate d){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedString = d.format(formatter);
        return formattedString;
//        return d.getDayOfMonth() + "/" + d.getMonthValue() + "/" + d.getYear() ;
    }

    /**
     * Reformats a date string to ensure day and month are zero-padded to two digits.
     *
     * @param dateStr the input date string in format d/M/yyyy
     * @return the zero-padded formatted date string in dd/MM/yyyy
     */
    public static String formatDate(String dateStr) {
        String[] dateParts = dateStr.split("/");

        // Pad day and month to be two digits long
        String day = String.format("%02d", Integer.parseInt(dateParts[0]));
        String month = String.format("%02d", Integer.parseInt(dateParts[1]));

        // Reconstruct the date string
        return day + "/" + month + "/" + dateParts[2];
    }

    /**
     * Checks if a string is null or empty after trimming.
     *
     * @param input the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
}
