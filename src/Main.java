
import User.User;

import User.UserBoundary;
import Login.LoginBoundary;
import Utils.SessionManager;
import static Utils.RepositoryGetter.*;

public class Main {
    public static void main(String[] args) {
        getUsersRepository().display();
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
