package Applicant;

import Abstract.Repository;
import Enumerations.MaritalStatus;
import Enumerations.Role;
import Utils.CsvUtils;
import User.User;

public class ApplicantRepository extends Repository<Applicant> {
    static ApplicantRepository instance;
    private ApplicantRepository(String filePath) {
        super(filePath);
    }

    public static ApplicantRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ApplicantRepository(filePath);
        }
        return instance;
    }

    @Override
    public Applicant fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        String nric = values[1];
        return new Applicant(values[0], age, status, nric);
    }

    @Override
    public String CSVHeader() {
        return "Name,NRIC,Age,Marital Status";
    }





}