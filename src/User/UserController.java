package User;
import User.UsersRepository;
import User.User;
import Applicant.Applicant;


import java.util.ArrayList;

import static Applicant.ApplicantController.getApplicantRepository;

public class UserController {

    private static UsersRepository getUsersRepository() {
        return new UsersRepository("./src/data/User.csv");
    }

    public static boolean changePassword(User user, String newPassword) {
        UsersRepository usersRepository = getUsersRepository();
        user.setPassword(newPassword);
        return usersRepository.update(user);
    }

    public static User login(String nric, String password) {
        UsersRepository usersRepository = getUsersRepository();
        User user = usersRepository.getByID(nric);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public static ArrayList<User> getAllUsers() {
        return getUsersRepository().getAllUsers();
    }

    public static Applicant createApplicant(User user){
        // get the applicants detaails from repo

        Applicant applicant = getApplicantRepository().getByID(user.getID());
        if (applicant != null){
            return applicant;
        }
        return null;
    }
}
