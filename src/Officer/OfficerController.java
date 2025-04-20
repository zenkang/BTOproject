package Officer;


import Applicant.Applicant;
import Enumerations.MaritalStatus;
import User.User;



import static Utils.RepositoryGetter.getOfficerRepository;

public class OfficerController {
    public static Officer createOfficer(User user){
        // get the applicants details from repo
        Officer officer = getOfficerRepository().getByID(user.getID());
        if (officer == null){
            System.out.println("Applicant not found");
            return null;
        }
        officer.setUserProfile(user);
        return officer;
    }
    public static boolean updateAge(Officer officer, int age){
        OfficerRepository officerRepository = getOfficerRepository();
        officer.setAge(age);
        return officerRepository.update(officer);
    }

    public static boolean updateName(Officer officer, String name){
        OfficerRepository officerRepository = getOfficerRepository();
        officer.setName(name);
        return officerRepository.update(officer);
    }

    public static boolean updateMaritalStatus(Officer officer, String maritalStatus){
        OfficerRepository officerRepository = getOfficerRepository();
        if(maritalStatus.equalsIgnoreCase("M")){
            officer.setMaritalStatus(MaritalStatus.MARRIED);
        }
        else if(maritalStatus.equalsIgnoreCase("S")){
            officer.setMaritalStatus(MaritalStatus.SINGLE);
        }
        return officerRepository.update(officer);
    }
    public static Officer getApplicantById(String id){
        return getOfficerRepository().getByID(id);
    }

}
