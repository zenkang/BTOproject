package Login;

import User.User;
import java.util.Scanner;

public class LoginBoundary {
    public static User login(){
        User user;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Enter NRIC: ");
            String nric = sc.nextLine();
            System.out.println("Enter Password: ");
            String password = sc.nextLine();
            user = LoginController.login(nric, password);
            if(user == null){
                System.out.println("Invalid NRIC or Password!\nTry again!\n");
            }
        } while (user == null);
        return user;
    }
}
