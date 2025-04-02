package Applicant;

import Abstract.Repository;
import Enumerations.MaritalStatus;
import Utils.CsvUtils;

public class ApplicantRepository extends Repository<Applicant> {
    public ApplicantRepository(String filePath) {
        super(filePath);
    }

    @Override
    public Applicant fromCSVRow(String row) {
        String[] values = row.split(",");
        Integer age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        return new Applicant(values[0],values[1],age,status,values[4]);
    }

    @Override
    public String CSVHeader() {
        return "Name,NRIC,Age,Marital Status,Password";
    }

    public boolean CreateApplicant(String name, String nric, int age, String Status, String password) {
        if(!CsvUtils.isValidMaritalStatus(Status)) {
            System.out.println("Invalid Marital Status");
            return false;
        }
        MaritalStatus maritalStatus = MaritalStatus.valueOf(Status.toUpperCase());
        Applicant app = new Applicant(name,nric,age,maritalStatus,password);
        return this.create(app);
    }

    public boolean deleteApplicantByNRIC(String nric) {
        Applicant app = this.getByID(nric);
        if(app == null) {
            return false;
        }
        return this.delete(app);
    }
}
