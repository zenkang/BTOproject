package Login;

import User.User;
import User.UsersRepository;

import static Utils.RepositoryGetter.getUsersRepository;
import static Utils.PasswordHasher.hashPassword;

public class LoginController {

    public static User login(String nric, String password) {
        UsersRepository usersRepository = getUsersRepository();
        User user = usersRepository.getByID(nric);
        String hashedPassword = hashPassword(password);
        if (user != null && user.getPassword().equals(hashedPassword)) {
            return user;
        }
        return null;
    }
}
