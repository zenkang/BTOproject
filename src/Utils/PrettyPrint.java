package Utils;

import Abstract.IUserProfile; // import your IUserProfile interface

/**
 * Utility class for formatting and printing detailed information about users and projects.
 * This class provides static methods to neatly print user profiles and project attributes
 * in a readable and consistent format for console-based interfaces.
 */
public class PrettyPrint {

    /**
     * Prints the full profile of a user implementing IUserProfile.
     * Includes name, NRIC, age, and marital status.
     *
     * @param user the user profile to be printed
     */
    public static void prettyPrint(IUserProfile user) {
        System.out.println("\n=== " + user.getClass().getSimpleName() + " Profile ===");
        System.out.println("Name: " + user.getName());
        System.out.println("NRIC: " + user.getNric());
        System.out.println("Age: " + user.getAge());
        System.out.println("MaritalStatus: " + user.getMaritalStatus().toString());
    }

    /**
     * Prints options for updating an existing user profile.
     * Shows current values for name, age, and marital status.
     *
     * @param user the user whose update options are being printed
     */
    public static void prettyUpdate(IUserProfile user) {
        System.out.println("1. Update Name: " + user.getName());
        System.out.println("2. Update Age: " + user.getAge());
        System.out.println("3. Update Marital Status: " + user.getMaritalStatus().toString());
        System.out.println("0. Back");
    }
}
