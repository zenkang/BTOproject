package Utils;

import Abstract.IUserProfile; // import your IUserProfile interface

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
}
