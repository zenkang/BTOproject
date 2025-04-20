package Officer;

import Abstract.Repository;

import Enumerations.MaritalStatus;


public class OfficerRepository extends Repository<Officer> {
    static OfficerRepository instance;
    private OfficerRepository(String filePath) {
        super(filePath);
    }
    public static OfficerRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new OfficerRepository(filePath);
        }
        return instance;
    }
    @Override
    public Officer fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        String nric = values[1];
        return new Officer(values[0], age, status, nric);
    }

    @Override
    public String CSVHeader() {
        return "Name,NRIC,Age,Marital Status";
    }



}
