package User;
import Manager.Manager;
import Manager.ManagerBoundary;

import Applicant.Applicant;
import Applicant.ApplicantBoundary;

import static Utils.RepositoryGetter.*;

public class UserController {

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
                Manager manager = UserController.createManager(user);
                ManagerBoundary view = new ManagerBoundary(manager);
                assert manager != null;
                System.out.println("\nWelcome " + manager.getName());
                view.displayMenu();
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




    public static Applicant createApplicant(User user){
        // get the applicants detaails from repo
        Applicant applicant = getApplicantRepository().getByID(user.getID());
        if (applicant != null){
            return applicant;
        }
        return null;
    }
    public static Manager createManager(User user){
        Manager manager = getManagerRepository().getByID(user.getID());
        if (manager != null){
            return manager;
        }
        return null;
    }
}
