package User;
import Manager.Manager;
import Manager.ManagerBoundary;
import Manager.ManagerController;

import Applicant.Applicant;
import Applicant.ApplicantController;
import Applicant.ApplicantBoundary;
import Officer.Officer;
import Utils.SessionManager;

import Officer.OfficerBoundary;
import Officer.OfficerController;
import static Utils.RepositoryGetter.*;

/**
 * The UserController class handles logic related to routing users to role-specific
 * menu interfaces and updating user credentials such as passwords.
 */
public class UserController {

    /**
     * Routes the authenticated user to their corresponding role-specific boundary interface.
     * Depending on the role (APPLICANT, OFFICER, MANAGER), it initializes the corresponding
     * controller and boundary class to display the user's menu.
     *
     * @param user the authenticated User object
     */
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
                Officer officer = OfficerController.createOfficer(user);
                if(officer!=null){
                    OfficerBoundary view = new OfficerBoundary(officer);
                    System.out.println("\nWelcome " + officer.getName());
                    view.displayMenu();
                }
                else{
                    System.out.println("Officer creation failed");
                }
            }
            case MANAGER ->{
                // create the manager and show the menu
                Manager manager = ManagerController.createManager(user);
                if(manager != null) {
                    ManagerBoundary view = new ManagerBoundary(manager);
                    System.out.println("\nWelcome " + manager.getName());
                    view.displayManagerMenu();
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

    /**
     * Changes the user's password and updates it in the repository.
     * Also flags that the password was changed during this session.
     *
     * @param user the user whose password is to be changed
     * @param newPassword the new password to assign
     * @return true if the password was successfully updated, false otherwise
     */
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
