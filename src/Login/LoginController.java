package Login;

import User.User;
import User.UsersRepository;

import static Utils.RepositoryGetter.getUsersRepository;

/**
 * Handles user authentication logic for login functionality.
 * Interacts with the UsersRepository to validate login credentials.
 */
public class LoginController {

    /**
     * Authenticates a user by checking if a user with the provided NRIC exists and if the password matches.
     *
     * @param nric the NRIC entered by the user
     * @param password the password entered by the user
     * @return the User object if authentication is successful, otherwise null
     */
    public static User login(String nric, String password) {
        UsersRepository usersRepository = getUsersRepository();
        User user = usersRepository.getByID(nric);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
