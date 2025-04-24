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
import static Utils.PasswordHasher.hashPassword;

/**
 * The UserController class handles logic related to routing users to role-specific
 * menu interfaces and updating user credentials such as passwords.
 */
public class UserController {

    /**
     * This method applies the Factory design pattern to create the appropriate
     * domain object
     * Routes the authenticated user to their corresponding role-specific boundary interface.
     * Depending on the role (APPLICANT, OFFICER, MANAGER), it initializes the corresponding
     * boundary class to display the user's menu.
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
     * Updates the password of the given user
     * The new password is hashed before being stored. If the update operation
     * fails in the underlying repository, the method returns false
     *
     * @param user        the User whose password is to be updated
     * @param newPassword the new password in plain text
     * @return true if the password was successfully changed; false otherwise
     */
    public static boolean changePassword(User user, String newPassword){
        UsersRepository usersRepository = getUsersRepository();
        String hashedPassword = hashPassword(newPassword);
        user.setPassword(hashedPassword);
        if(!usersRepository.update(user)){
            return false;
        }
        else{
            SessionManager.passwordChanged = true;
            return true;
        }
    }


}
