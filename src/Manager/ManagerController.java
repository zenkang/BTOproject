package Manager;


import Enumerations.MaritalStatus;
import User.User;

import static Utils.RepositoryGetter.*;

public class ManagerController {

    public static Manager createManager(User user){
        Manager manager = getManagerRepository().getByID(user.getID());
        if (manager == null){
            System.out.println("Manager not found");
            return null;
        }
        manager.setUserProfile(user);
        return manager;
    }


    public static boolean updateAge(Manager manager, int age){
        manager.setAge(age);
        return getManagerRepository().update(manager);
    }

    public static boolean updateName(Manager manager, String name){
        manager.setName(name);
        return getManagerRepository().update(manager);
    }

    public static boolean updateMaritalStatus(Manager manager, String maritalStatus){
        if(maritalStatus.equalsIgnoreCase("M")){
            manager.setMaritalStatus(MaritalStatus.MARRIED);
        }
        else if(maritalStatus.equalsIgnoreCase("S")){
            manager.setMaritalStatus(MaritalStatus.SINGLE);
        }
        return getManagerRepository().update(manager);
    }
    public static String getNameById(String id){
        Manager man = getManagerRepository().getByID(id);
        if (man == null){
            System.out.println("Manager not found");
            return null;
        }
        return man.getName();
    }
}
