package Utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Project.Project;
import Project.ProjectController;
import static Utils.RepositoryGetter.*;



/**
 * Utility class for safe and validated input handling from the console.
 * Provides methods to ensure robust input validation for integers, strings, and passwords.
 */
public class SafeScanner {

    /**
     * Prompts the user for an integer input within a specified range.
     *
     * @param scanner Scanner instance to read input.
     * @param prompt  The message to prompt the user.
     * @param min     The minimum allowed value.
     * @param max     The maximum allowed value.
     * @return A validated integer input within the range.
     */
    public static int getValidatedIntInput(Scanner scanner, String prompt, int min, int max) {
        int input = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            try {
                input = scanner.nextInt();

                // Check if the input is within the range
                if (input >= min && input <= max) {
                    valid = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear the invalid input
            }
        }

        // Clear the buffer after reading an integer to handle any lingering newline
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }

        return input;
    }
    public static double getValidatedDoubleInput(Scanner scanner, String prompt, double min, double max) {
        double input = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            String userInput = scanner.nextLine();

            // Regex to match only non-negative decimal numbers (e.g., 2.5, 0.01)
            if (userInput.matches("\\d+\\.\\d+")) {
                try {
                    input = Double.parseDouble(userInput);

                    if (input >= min && input <= max) {
                        valid = true;
                    } else {
                        System.out.println("Please enter a number between " + min + " and " + max + ".");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid decimal number.");
                }
            } else {
                System.out.println("Invalid format. Please enter a positive decimal number (e.g., 2.5).");
            }
        }

        return input;
    }

    /**
     * Prompts the user for a string input with validation for length and illegal characters.
     *
     * @param scanner   Scanner instance to read input.
     * @param prompt    The message to prompt the user.
     * @param maxLength The maximum allowed length of the string.
     * @return A validated string input.
     */
    public static String getValidatedStringInput(Scanner scanner, String prompt, int maxLength) {
        String input = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim(); // Get the line and trim spaces

            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a non-empty string.");
            } else if (input.length() > maxLength) {
                System.out.println("Input is too long. Maximum length allowed is " + maxLength + " characters.");
            } else if (input.contains(",")) {
                System.out.println("Invalid input. Commas are not allowed.");
            } else {
                valid = true;
            }
        }

        return input;
    }


    /**
     * Prompts the user for a string input from a predefined list of valid options.
     *
     * @param scanner     Scanner instance to read input.
     * @param prompt      The message to prompt the user.
     * @param validInputs A list of valid string inputs.
     * @return A validated string input matching one of the valid options.
     */
    public static String getValidatedStringInput(Scanner scanner, String prompt, List<String> validInputs) {
        String input = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim().toLowerCase(); // Get the line and trim spaces

            if (input.isEmpty()) {
                System.out.println("Invalid input. Please enter a non-empty string.");
            } else if (!validInputs.contains(input)) {
                System.out.println("Invalid input. Accepted values are: " + validInputs);
            } else if (input.contains(",")) {
                System.out.println("Invalid input. Commas are not allowed.");
            } else {
                valid = true;
            }
        }

        return input;
    }


    /**
     * Prompts the user to input a strong password.
     *
     * @param scanner Scanner instance to read input.
     * @param prompt  The message to prompt the user.
     * @return A validated strong password.
     */
    public static String getStrongPassword(Scanner scanner, String prompt) {
        String password = "";
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            password = readPasswordMasked(scanner); // Read and mask password input
            if (validatePassword(password)) {
                valid = true;
            }
        }

        return password;
    }


    /**
     * Validates the password against strength criteria.
     *
     * @param password The password to validate.
     * @return True if the password meets all strength criteria; otherwise, false.
     */
    private static boolean validatePassword(String password) {
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        } else if (!password.matches(".*[A-Z].*")) {
            System.out.println("Password must contain at least one uppercase letter.");
            return false;
        } else if (!password.matches(".*[a-z].*")) {
            System.out.println("Password must contain at least one lowercase letter.");
            return false;
        } else if (!password.matches(".*\\d.*")) {
            System.out.println("Password must contain at least one digit.");
            return false;
        } else if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            System.out.println("Password must contain at least one special character (e.g., !@#$%^&*).");
            return false;
        }
        return true;
    }


    /**
     * Reads a password input from the console, masking the input.
     *
     * @param scanner Scanner instance to read input.
     * @return The entered password as a string.
     */
    public static String readPasswordMasked(Scanner scanner) {
        StringBuilder password = new StringBuilder();
        try {
            while (true) {
                char ch = (char) System.in.read(); // Read one character at a time
                if (ch == '\n') break; // Enter key signals end of input
                if (ch == '\b' && password.length() > 0) {
                    password.deleteCharAt(password.length() - 1); // Handle backspace
                    System.out.print("\b \b"); // Erase character from console
                } else if (ch != '\b') {
                    password.append(ch);
                    System.out.print("*"); // Print `*` for each character
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(); // Move to the next line
        return password.toString().trim();
    }


    /**
     * Reads a password input from the console with a prompt, masking the input.
     *
     * @param scanner Scanner instance to read input.
     * @param prompt  The message to prompt the user.
     * @return The entered password as a string.
     */
    public static String readPasswordMasked(Scanner scanner, String prompt) {
        System.out.print(prompt);

        StringBuilder password = new StringBuilder();
        try {
            while (true) {
                char ch = (char) System.in.read(); // Read one character at a time
                if (ch == '\n') break; // Enter key signals end of input
                if (ch == '\b' && password.length() > 0) {
                    password.deleteCharAt(password.length() - 1); // Handle backspace
                    System.out.print("\b \b"); // Erase character from console
                } else if (ch != '\b') {
                    password.append(ch);
                    System.out.print("*"); // Print `*` for each character
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(); // Move to the next line
        return password.toString().trim();
    }


    public static String getValidProjectName(Scanner scanner) {
        String projectName;
        Project project;

        while (true) {
            System.out.print("Enter Project Name: ");
            projectName = scanner.nextLine().trim();

            project = ProjectController.getProjectByName(projectName);
            if (project != null) {
                return projectName;
            } else {
                System.out.println("Invalid Project Name. Please try again.");
            }
        }
    }
    public static String[] getValidatedOfficerNames(Scanner scanner, int expectedCount) {
        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
                continue;
            }

            String[] names = input.split(",");
            for (int i = 0; i < names.length; i++) {
                names[i] = names[i].trim();
            }

            if (names.length != expectedCount) {
                System.out.println("Invalid input: you must enter exactly " + expectedCount + " names. You entered " + names.length + ".");
                System.out.print("Enter Officers (comma-separated): ");
                continue;
            }

            boolean valid = true;
            for (String name : names) {
                if (name.isEmpty()) {
                    System.out.println("One or more names are empty. Please ensure all names are non-empty.");
                    valid = false;
                    break;
                }
            }

            if (valid) {
                return names;
            }
        }
    }
    /**
     * Prompts the user for a date input in the specified format and validates it.
     *
     * @param scanner The Scanner instance to read input.
     * @param prompt  The message to prompt the user.
     * @return A validated date string in "dd/MM/yy" format.
     */
    public static LocalDate getValidDate(Scanner scanner, String prompt) {
        System.out.print(prompt);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean valid = false;
        LocalDate date = null;
        while (!valid) {
            try {
                String input = scanner.nextLine().trim();
                date = LocalDate.parse(input, dateFormatter);
                valid = true; // Set to true if parsing is successful
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a valid date (DD/MM/YYYY).");
            }
        }
        return date;
    }

    public static LocalDate getValidDateAfterDate(Scanner scanner,LocalDate startDate, String prompt) {
        System.out.print(prompt);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean valid = false;
        LocalDate date = null;
        while (!valid) {
            try {
                String input = scanner.nextLine().trim();
                date = LocalDate.parse(input, dateFormatter);
                if (date.isAfter(startDate)) {
                valid = true; // Set to true if parsing is successful
                }
                else{
                    System.out.println("Error, date must be after start date!\nPlease enter a valid date (DD/MM/YYYY): ");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter a valid date (DD/MM/YYYY).");
            }
        }
        return date;
    }

    public static String getValidProjectID(Scanner sc) {
        String projectID;
        Project project;

        do {
            projectID = sc.nextLine().trim();
            project = ProjectController.getProjectByID(projectID);
            if (project == null) {
                System.out.println("No Project found with ID: " + projectID + ". Please Enter a valid Project ID again.");
            }
        } while (project == null);

        return projectID;
    }

    public static String getValidApplicationID(Scanner sc, String prompt) {
        System.out.print(prompt);
        String applicationID;
        do{
            applicationID = sc.nextLine().trim();
            if(getProjectApplicationsRepository().getByID(applicationID) == null) {
                System.out.println("Invalid application ID. Please enter a valid application ID.");
            }
        }while (getProjectApplicationsRepository().getByID(applicationID) == null);
        return applicationID;
    }

}