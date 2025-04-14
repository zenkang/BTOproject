package Manager;


import Applicant.Applicant;
import Enumerations.MaritalStatus;

import static Utils.RepositoryGetter.*;

public class ManagerController {
    public static boolean updateAge(Manager manager, int age){
        ManagerRepository applicantRepository = getManagerRepository();
        manager.setAge(age);
        return applicantRepository.update(manager);
    }

    public static boolean updateName(Manager manager, String name){
        ManagerRepository applicantRepository = getManagerRepository();
        manager.setName(name);
        return applicantRepository.update(manager);
    }

    public static boolean updateMaritalStatus(Manager manager, String maritalStatus){
       ManagerRepository applicantRepository = getManagerRepository();
        if(maritalStatus.equalsIgnoreCase("M")){
            manager.setMaritalStatus(MaritalStatus.MARRIED);
        }
        else if(maritalStatus.equalsIgnoreCase("S")){
            manager.setMaritalStatus(MaritalStatus.SINGLE);
        }
        return applicantRepository.update(manager);
    }
}
