package Applicant;

import Enumerations.MaritalStatus;
import User.User;

import static Utils.RepositoryGetter.*;

/**
 * Controller class for managing operations related to Applicant entities.
 * Handles creation and updates of applicant profiles using data from the repository.
 */
public class ApplicantController {

    /**
     * Creates an Applicant object using the given User object.
     * Links the User profile to the applicant and returns the updated Applicant.
     *
     * @param user the User object containing login credentials
     * @return the Applicant object with linked user profile, or null if not found
     */
    public static Applicant createApplicant(User user){
        // get the applicants details from repo
        Applicant applicant = getApplicantRepository().getByID(user.getID());
        if (applicant == null){
            System.out.println("Applicant not found");
            return null;
        }
        applicant.setUserProfile(user);
        return applicant;
    }

    /**
     * Updates the age of the given applicant and persists the change in the repository.
     *
     * @param applicant the applicant to update
     * @param age the new age to set
     * @return true if the update was successful, false otherwise
     */
    public static boolean updateAge(Applicant applicant, int age){
        ApplicantRepository applicantRepository = getApplicantRepository();
        applicant.setAge(age);
        return applicantRepository.update(applicant);
    }

    /**
     * Updates the name of the given applicant and persists the change in the repository.
     *
     * @param applicant the applicant to update
     * @param name the new name to set
     * @return true if the update was successful, false otherwise
     */
    public static boolean updateName(Applicant applicant, String name){
        ApplicantRepository applicantRepository = getApplicantRepository();
        applicant.setName(name);
        return applicantRepository.update(applicant);
    }

    /**
     * Updates the marital status of the given applicant based on a string input ("M" or "S").
     *
     * @param applicant the applicant to update
     * @param maritalStatus a string representing the new marital status ("M" for Married, "S" for Single)
     * @return true if the update was successful, false otherwise
     */
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

    /**
     * Retrieves an Applicant object by its ID from the repository.
     *
     * @param id the unique identifier (NRIC) of the applicant
     * @return the corresponding Applicant object, or null if not found
     */
    public static Applicant getApplicantById(String id){
        return getApplicantRepository().getByID(id);
    }


}
