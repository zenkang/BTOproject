package Utils;

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
