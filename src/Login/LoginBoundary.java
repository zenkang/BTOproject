package Login;

import User.User;
import java.util.Scanner;

import static Utils.BoundaryStrings.footer;
import static Utils.BoundaryStrings.logo;

/**
 * Handles user interface and interaction for the login process.
 * Displays the system logo, prompts for user credentials, and invokes the LoginController.
 */
public class LoginBoundary {

    /**
     * Displays the login screen and repeatedly prompts the user until valid credentials are provided.
     * Utilizes the LoginController to authenticate the user based on NRIC and password.
     *
     * @return the authenticated User object, or null if login fails
     */
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
