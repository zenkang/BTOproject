package Enquiry;

import Abstract.Repository;

public class EnquiryRepository extends Repository<Enquiry> {
    private static EnquiryRepository instance;

    private EnquiryRepository(String filePath) {
        super(filePath);
    }

    public static EnquiryRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new EnquiryRepository(filePath);
        }
        return instance;
    }

    @Override
    public Enquiry fromCSVRow(String row) {
        return (Enquiry) new Enquiry("", "", "", "").fromCSVRow(row);
    }

    @Override
    public String CSVHeader() {
        return "EnquiryID,ApplicantNRIC,ProjectName,Message,Reply";
    }
}
