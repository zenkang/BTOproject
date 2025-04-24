package Officer;



import Enumerations.MaritalStatus;
import User.User;



import static Utils.RepositoryGetter.getOfficerRepository;

/**
 * OfficerController handles the business logic and operations related to Officer users.
 * It interacts with the OfficerRepository for data persistence and updates,
 * and facilitates profile data retrieval and modifications.
 */
public class OfficerController {

    /**
     * Retrieves an existing Officer from the repository and attaches the provided User profile.
     *
     * @param user the User object representing login credentials and role
     * @return the corresponding Officer object if found, otherwise null
     */
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

    /**
     * Updates the age of the given Officer and saves the changes to the repository.
     *
     * @param officer the Officer whose age is to be updated
     * @param age     the new age to set
     * @return true if update is successful, false otherwise
     */
    public static boolean updateAge(Officer officer, int age){
        OfficerRepository officerRepository = getOfficerRepository();
        officer.setAge(age);
        return officerRepository.update(officer);
    }

    /**
     * Updates the name of the given Officer and saves the changes to the repository.
     *
     * @param officer the Officer whose name is to be updated
     * @param name    the new name to set
     * @return true if update is successful, false otherwise
     */
    public static boolean updateName(Officer officer, String name){
        OfficerRepository officerRepository = getOfficerRepository();
        officer.setName(name);
        return officerRepository.update(officer);
    }

    /**
     * Updates the marital status of the given Officer and saves the changes to the repository.
     *
     * @param officer        the Officer whose marital status is to be updated
     * @param maritalStatus  the new marital status ("M" for Married, "S" for Single)
     * @return true if update is successful, false otherwise
     */
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

    /**
     * Retrieves an Officer by their NRIC (ID).
     *
     * @param id the NRIC of the officer
     * @return the Officer object if found, otherwise null
     */
    public static Officer getApplicantById(String id){
        return getOfficerRepository().getByID(id);
    }

}
