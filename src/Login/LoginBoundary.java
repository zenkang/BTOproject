package Login;

import User.User;
import java.util.Scanner;

import static Utils.BoundaryStrings.footer;
import static Utils.BoundaryStrings.logo;

public class LoginBoundary {
    public static User login(){
        User user;
        Scanner sc = new Scanner(System.in);

        System.out.println("\n\n");
        System.out.print(logo);

        // Footer
        System.out.println(footer);
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
