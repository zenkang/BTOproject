package Utils;

import Abstract.IUserProfile; // import your IUserProfile interface
import Project.Project;

import java.time.format.DateTimeFormatter;

public class PrettyPrint {
    public static void prettyPrint(IUserProfile user) {
        System.out.println("\n=== " + user.getClass().getSimpleName() + " Profile ===");
        System.out.println("Name: " + user.getName());
        System.out.println("NRIC: " + user.getNric());
        System.out.println("Age: " + user.getAge());
        System.out.println("MaritalStatus: " + user.getMaritalStatus().toString());
    }

    public static void prettyUpdate(IUserProfile user) {
        System.out.println("1. Update Name: " + user.getName());
        System.out.println("2. Update Age: " + user.getAge());
        System.out.println("3. Update Marital Status: " + user.getMaritalStatus().toString());
        System.out.println("0. Back");
    }
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
