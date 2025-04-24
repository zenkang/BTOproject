package User;

import java.util.ArrayList;
import java.util.Scanner;

import Utils.SafeScanner;

/**
 * The UserBoundary class handles user-facing interactions related to general user operations,
 * such as routing to role-specific menus and changing passwords.
 * This class acts as the boundary layer in the Entity–Boundary–Controller (EBC) architecture.
 */
public class UserBoundary {

    /**
     * Routes the user to the appropriate UI menu based on their role.
     * Delegates routing logic to the UserController.
     *
     * @param user the authenticated user to be routed
     */
    public static void route(User user) {
        System.out.println("Loading " + user.getRole().toString() + " menu...");
        UserController.route(user);
    }

    /**
     * Prompts the user to change their password using a secure password input method.
     * Validates the new password using the SafeScanner utility and delegates
     * the change operation to the UserController.
     *
     * @param user the user requesting a password change
     */
    public static void changePassword(User user){
        Scanner sc = new Scanner(System.in);
        String NewPassword = SafeScanner.getStrongPassword(sc,"Enter new Password:\n");
        String confirmPassword = SafeScanner.getValidatedStringInput(sc,"Confirm password: ",50);
        while(!confirmPassword.equals(NewPassword)){
            System.out.println("Passwords do not match. Please try again.");
            NewPassword = SafeScanner.getStrongPassword(sc,"Enter new Password:\n");
            confirmPassword = SafeScanner.getValidatedStringInput(sc,"Confirm password: ",50);
        }
        if(UserController.changePassword(user,NewPassword)){
            System.out.println("Password changed!");
            System.out.println("Password changed Successfully!\nPlease proceed to login again.");
        }
        else{
            System.out.println("Password change failed!");
        }
    }


}
