package User;
import User.UsersRepository;
import User.User;
import Applicant.Applicant;
import Applicant.ApplicantBoundary;

import java.util.ArrayList;

import static Applicant.ApplicantController.getApplicantRepository;

public class UserController {

    private static UsersRepository getUsersRepository() {
        return new UsersRepository("./src/data/User.csv");
    }
    public static void route(User user) {
        switch(user.getRole()){
            case APPLICANT ->{
                // create the applicant and show the menu
                Applicant app = UserController.createApplicant(user);
                ApplicantBoundary view = new ApplicantBoundary(app);
                assert app != null;
                System.out.println("\nWelcome " + app.getName());
                view.displayMenu();
            }
            case OFFICER ->{
                // create the officer and show the menu
            }
            case MANAGER ->{
                // create the manager and show the menu
            }
            default -> {
                // do shit
                return;
            }

        }
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
