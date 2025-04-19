
import User.User;

import User.UserBoundary;
import Login.LoginBoundary;
import Utils.SessionManager;

public class Main {
    public static void main(String[] args) {
        do {
            SessionManager.passwordChanged = false;
            User user = LoginBoundary.login();
            if (user == null) {
                return;
            }
            System.out.println(user);
            UserBoundary.route(user);
        }while (SessionManager.passwordChanged);
            System.out.println("end");

    }
}
