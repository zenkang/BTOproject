package Login;

import User.User;
import User.UsersRepository;
import java.util.ArrayList;

import static Utils.RepositoryGetter.getUsersRepository;

public class LoginController {

    public static ArrayList<User> getAllUsers() {
        return getUsersRepository().getAllUsers();
    }
    public static User login(String nric, String password) {
        UsersRepository usersRepository = getUsersRepository();
        User user = usersRepository.getByID(nric);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
