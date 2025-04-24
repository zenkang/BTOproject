
import User.User;

import User.UserBoundary;
import Login.LoginBoundary;
import Utils.SessionManager;

import static Utils.BoundaryStrings.BYE_LOGO;


/**
 * The Main class serves as the program entry point.
 *
 * It handles the login flow and routes authenticated users to their respective
 * role-based interfaces. If a user's password is changed during the session,
 * the program will prompt them to log in again.
 */
public class Main {

    /**
     * Starts the application.
     * The main loop handles user login and routing. If a password change occurs
     * during the session, it forces a re-login by repeating the login loop.
     * Upon exit, a goodbye logo is printed to the console.
     *
     * @param args the command-line arguments (not used)
     */
    public static void main(String[] args) {
        do {
            SessionManager.passwordChanged = false;
            User user = LoginBoundary.login();
            if (user == null) {
                return;
            }

            UserBoundary.route(user);
        }while (SessionManager.passwordChanged);
        System.out.println(BYE_LOGO);
    }
}
