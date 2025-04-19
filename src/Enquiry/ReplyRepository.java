package Enquiry;

import Abstract.Repository;

public class ReplyRepository extends Repository<Reply> {
    private static ReplyRepository instance;

    private ReplyRepository(String filePath) {
        super(filePath);
    }

    public static ReplyRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ReplyRepository(filePath);
        }
        return instance;
    }

    @Override
    public String CSVHeader() {
        return "ReplyID,EnquiryID,Date,Officer/ManagerID,Reply";
    }

    @Override
    public Reply fromCSVRow(String row) {
        return (Reply) new Reply("", "", null, "", "").fromCSVRow(row);
    }

}
