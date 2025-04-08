package User;

import java.util.ArrayList;
import java.util.Scanner;

import Applicant.Applicant;
import Utils.SafeScanner;
import Applicant.ApplicantBoundary;

public class UserBoundary {

    public static void route(User user) {

        System.out.println("Loading " + user.getRole().toString() + " menu...");
        UserController.route(user);
    }

    public static User login(){
        User user = null;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Enter NRIC: ");
            String nric = sc.nextLine();
            System.out.println("Enter Password: ");
            String password = sc.nextLine();
            user = UserController.login(nric, password);
            if(user == null){
                System.out.println("Invalid NRIC or Password!\nTry again!\n");
            }
        } while (user == null);
        return user;
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

    public static void displayUsers(){
        ArrayList<User> users = UserController.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

}
