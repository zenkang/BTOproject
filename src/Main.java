
import User.User;

import User.UserBoundary;
import Login.LoginBoundary;
import Utils.SessionManager;

import static Utils.BoundaryStrings.BYE_LOGO;


public class Main {

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
