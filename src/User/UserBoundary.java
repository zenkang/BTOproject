package User;

import java.util.ArrayList;
import java.util.Scanner;

import Utils.SafeScanner;


public class UserBoundary {

    public static void route(User user) {
        System.out.println("Loading " + user.getRole().toString() + " menu...");
        UserController.route(user);
    }

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
