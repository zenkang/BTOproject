package Applicant;

import Enumerations.MaritalStatus;

public class ApplicantController {

    public static ApplicantRepository getApplicantRepository(){
        return new ApplicantRepository("./src/data/ApplicantList.csv");
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


}
