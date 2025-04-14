package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public static String getFmtDate(LocalDate d){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedString = d.format(formatter);
        return formattedString;
//
//        return d.getDayOfMonth() + "/" + d.getMonthValue() + "/" + d.getYear() ;
    }
    public static String formatDate(String dateStr) {
        String[] dateParts = dateStr.split("/");

        // Pad day and month to be two digits long
        String day = String.format("%02d", Integer.parseInt(dateParts[0]));
        String month = String.format("%02d", Integer.parseInt(dateParts[1]));

        // Reconstruct the date string
        return day + "/" + month + "/" + dateParts[2];
    }
    //input validation
    public static boolean isValidMaritalStatus(String input) {
        if (isNullOrEmpty(input)) {
            return false;
        }
        // Normalize the input using CsvUtils.capitalizeFirstLetter
        String normalized = CsvUtils.capitalizeFirstLetter(input);
        return normalized.equals("Married") || normalized.equals("Single");
    }

    public static boolean isNullOrEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }
}
