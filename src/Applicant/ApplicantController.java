package Applicant;

public class ApplicantController {
    public static ApplicantRepository getApplicantRepository(){
        return new ApplicantRepository("./src/data/ApplicantList.csv");
    }
}
