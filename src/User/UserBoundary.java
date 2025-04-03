package User;

import java.util.ArrayList;
import java.util.Scanner;

import Applicant.Applicant;
import Utils.SafeScanner;
import Applicant.ApplicantBoundary;

public class UserBoundary {

    public static void route(User user) {
        switch(user.getRole()){
            case APPLICANT ->{
                // create the applicant and show the menu
                Applicant app = UserController.createApplicant(user);
                ApplicantBoundary view = new ApplicantBoundary(app);
                view.displayMenu();
            }
            case OFFICER ->{
                // create the applicant and show the menu
            }
            case MANAGER ->{
                // create the applicant and show the menu
            }
            default -> {
                // do shit
            }

        }
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
        sc.close();
        return user;
    }

    public static User changePassword(User user){
        Scanner sc = new Scanner(System.in);
        String NewPassword = SafeScanner.getStrongPassword(sc,"Enter new Password");
        if(UserController.changePassword(user,NewPassword)){
            System.out.println("Password changed!");
        }
        else{
            System.out.println("Password change failed!");
        }
        sc.close();
        return user;
    }

    public static void displayUsers(){
        ArrayList<User> users = UserController.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
    }

}
