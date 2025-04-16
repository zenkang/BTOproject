package Enquiry;

import Abstract.Repository;

import java.util.List;

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
        return (Enquiry) new Enquiry("", null, "", "", "").fromCSVRow(row);
    }

    @Override
    public String CSVHeader() {
        return "EnquiryID,Date,ProjectName,ApplicantID,Question,Status";
    }

    public List<Enquiry> getUnrepliedByProjectNames(List<String> projectNames) {
        return this.getByFilter(e ->
                projectNames.stream().anyMatch(
                        name -> name.trim().equalsIgnoreCase(e.getProjectName().trim())
                ) && !e.isReplied()
        );
    }

}
