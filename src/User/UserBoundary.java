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
        if(UserController.changePassword(user,NewPassword)){
            System.out.println("Password changed!");
        }
        else{
            System.out.println("Password change failed!");
        }
    }



}
