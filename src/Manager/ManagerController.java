package Manager;


import Enumerations.MaritalStatus;
import User.User;

import static Utils.RepositoryGetter.*;

/**
 * The ManagerController class provides static methods for managing Manager entities.
 * This includes creation, profile updates, and data retrieval by ID.
 */
public class ManagerController {

    /**
     * Creates and returns a Manager object using the associated User profile.
     * If no Manager is found for the given User ID, returns null.
     *
     * @param user the User object representing the manager's account
     * @return the Manager object or null if not found
     */
    public static Manager createManager(User user){
        Manager manager = getManagerRepository().getByID(user.getID());
        if (manager == null){
            System.out.println("Manager not found");
            return null;
        }
        manager.setUserProfile(user);
        return manager;
    }


    /**
     * Updates the age of the specified manager.
     *
     * @param manager the Manager whose age will be updated
     * @param age     the new age to set
     * @return true if update is successful, false otherwise
     */
    public static boolean updateAge(Manager manager, int age){
        manager.setAge(age);
        return getManagerRepository().update(manager);
    }

    /**
     * Updates the name of the specified manager.
     *
     * @param manager the Manager whose name will be updated
     * @param name    the new name to set
     * @return true if update is successful, false otherwise
     */
    public static boolean updateName(Manager manager, String name){
        manager.setName(name);
        return getManagerRepository().update(manager);
    }

    /**
     * Updates the marital status of the specified manager.
     *
     * @param manager       the Manager whose marital status will be updated
     * @param maritalStatus a string representing the new marital status ("M" or "S")
     * @return true if update is successful, false otherwise
     */
    public static boolean updateMaritalStatus(Manager manager, String maritalStatus){
        if(maritalStatus.equalsIgnoreCase("M")){
            manager.setMaritalStatus(MaritalStatus.MARRIED);
        }
        else if(maritalStatus.equalsIgnoreCase("S")){
            manager.setMaritalStatus(MaritalStatus.SINGLE);
        }
        return getManagerRepository().update(manager);
    }

    /**
     * Retrieves the name of a manager by their ID.
     *
     * @param id the manager's NRIC/ID
     * @return the manager's name if found, otherwise null
     */
    public static String getNameById(String id){
        Manager man = getManagerRepository().getByID(id);
        if (man == null){
            System.out.println("Manager not found");
            return null;
        }
        return man.getName();
    }
}
