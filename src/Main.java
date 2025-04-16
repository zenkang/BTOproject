
import User.User;

import User.UserBoundary;
import Login.LoginBoundary;
public class Main {
    public static void main(String[] args) {
        User user = LoginBoundary.login();
        if (user == null) {
            return;
        }
        System.out.println(user);
        UserBoundary.route(user);
        System.out.println("end");

    }
}
