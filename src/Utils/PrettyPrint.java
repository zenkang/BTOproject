package Utils;

import Abstract.IUserProfile; // import your IUserProfile interface
import Project.Project;

import java.time.format.DateTimeFormatter;

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

    /**
     * Prints the full details of a given project, including types, prices,
     * unit counts, application dates, and associated manager ID.
     *
     * @param project the project to print
     */
    public static void prettyPrintProject(Project project) {
        if (project == null) {
            System.out.println("Project not found.");
            return;
        }

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy");

        System.out.println("\n=== Project Details ===");
        System.out.println("Project ID: " + project.getID());
        System.out.println("Project Name: " + project.getProjectName());
        System.out.println("Neighbourhood: " + project.getNeighbourhood());
        System.out.println("Room Type 1: " + project.getType1());
        System.out.println("No. of Units (Type 1): " + project.getNoOfUnitsType1());
        System.out.println("Selling Price (Type 1): $" + project.getSellPriceType1());
        System.out.println("Room Type 2: " + project.getType2());
        System.out.println("No. of Units (Type 2): " + project.getNoOfUnitsType2());
        System.out.println("Selling Price (Type 2): $" + project.getSellPriceType2());
        System.out.println("Application Open Date: " + project.getAppDateOpen().format(fmt));
        System.out.println("Application Close Date: " + project.getAppDateClose().format(fmt));
        System.out.println("Manager ID: " + project.getManagerID());
        System.out.println("---------------------------");
    }
}
