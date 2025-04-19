package User;
import Manager.Manager;
import Manager.ManagerBoundary;
import Manager.ManagerController;

import Applicant.Applicant;
import Applicant.ApplicantBoundary;
import Applicant.ApplicantController;
import Utils.SessionManager;

import static Utils.RepositoryGetter.*;

public class UserController {

    public static void route(User user) {
        switch(user.getRole()){
            case APPLICANT ->{
                // create the applicant and show the menu
                Applicant app = ApplicantController.createApplicant(user);
                if(app!=null){
                    ApplicantBoundary view = new ApplicantBoundary(app);
                    System.out.println("\nWelcome " + app.getName());
                    view.displayMenu();
                }
                else{
                    System.out.println("Applicant creation failed");
                }
            }
            case OFFICER ->{
                // create the officer and show the menu
            }
            case MANAGER ->{
                // create the manager and show the menu
                Manager manager = ManagerController.createManager(user);
                if(manager != null) {
                    ManagerBoundary view = new ManagerBoundary(manager);
                    System.out.println("\nWelcome " + manager.getName());
                    view.displayMenu();
                }
                else{
                    System.out.println("Manager creation failed");
                }
            }
            default -> {
                // do shit
                return;
            }

        }
    }
    public static boolean changePassword(User user, String newPassword){
        UsersRepository usersRepository = getUsersRepository();
        user.setPassword(newPassword);
        if(!usersRepository.update(user)){
            return false;
        }
        else{
            SessionManager.passwordChanged = true;
            return true;
        }
    }


}
