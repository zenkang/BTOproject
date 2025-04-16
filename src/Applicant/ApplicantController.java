package Applicant;

import Enumerations.MaritalStatus;
import User.User;

import static Utils.RepositoryGetter.*;


public class ApplicantController {

    public static Applicant createApplicant(User user){
        // get the applicants detaails from repo
        Applicant applicant = getApplicantRepository().getByID(user.getID());
        if (applicant == null){
            System.out.println("Applicant not found");
            return null;
        }
        applicant.setUserProfile(user);
        return applicant;
    }


    public static boolean updateAge(Applicant applicant, int age){
        ApplicantRepository applicantRepository = getApplicantRepository();
        applicant.setAge(age);
        return applicantRepository.update(applicant);
    }

    public static boolean updateName(Applicant applicant, String name){
        ApplicantRepository applicantRepository = getApplicantRepository();
        applicant.setName(name);
        return applicantRepository.update(applicant);
    }

    public static boolean updateMaritalStatus(Applicant applicant, String maritalStatus){
        ApplicantRepository applicantRepository = getApplicantRepository();
        if(maritalStatus.equalsIgnoreCase("M")){
            applicant.setMaritalStatus(MaritalStatus.MARRIED);
        }
        else if(maritalStatus.equalsIgnoreCase("S")){
            applicant.setMaritalStatus(MaritalStatus.SINGLE);
        }
        return applicantRepository.update(applicant);
    }

    public static Applicant getApplicantById(String id){
        return getApplicantRepository().getByID(id);
    }


}
