package Applicant;

import Abstract.Repository;
import Enumerations.MaritalStatus;
import Enumerations.Role;
import Utils.CsvUtils;
import User.User;

public class ApplicantRepository extends Repository<Applicant> {
    public ApplicantRepository(String filePath) {
        super(filePath);
    }

    @Override
    public Applicant fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        // Create a User with an empty password; actual password is managed in User.csv
        User user = new User(values[1], "", Role.APPLICANT);
        return new Applicant(values[0], age, status, user);
    }

    @Override
    public String CSVHeader() {
        return "Name,NRIC,Age,Marital Status";
    }


    //What is this for? --Jarrel
    public boolean CreateApplicant(String name, String nric, int age, String Status, String password) {
        if(!CsvUtils.isValidMaritalStatus(Status)) {
            System.out.println("Invalid Marital Status");
            return false;
        }
        MaritalStatus maritalStatus = MaritalStatus.valueOf(Status.toUpperCase());
        Applicant app = new Applicant(name,nric,age,maritalStatus,password);
        return this.create(app);
    }

    //What is this for? --Jarrel
    public boolean deleteApplicantByNRIC(String nric) {
        Applicant app = this.getByID(nric);
        if(app == null) {
            return false;
        }
        return this.delete(app);
    }
}