package Reply;

import Abstract.Repository;

/**
 * The ReplyRepository class handles persistence and retrieval of Reply objects
 * from a CSV data source. It extends the generic Repository class to provide
 * entity-specific behavior for replies in the system.
 */
public class ReplyRepository extends Repository<Reply> {
    private static ReplyRepository instance;

    /**
     * Private constructor for singleton pattern.
     *
     * @param filePath the path to the CSV file used for persistence
     */
    private ReplyRepository(String filePath) {
        super(filePath);
    }

    /**
     * Returns the singleton instance of ReplyRepository, creating it if necessary.
     *
     * @param filePath the file path to the CSV source (only used on first call)
     * @return the singleton instance of ReplyRepository
     */
    public static ReplyRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ReplyRepository(filePath);
        }
        return instance;
    }

    /**
     * Provides the CSV header row for exporting Reply data.
     *
     * @return a String representing the CSV header
     */
    @Override
    public String CSVHeader() {
        return "ReplyID,EnquiryID,Date,Officer/ManagerID,Reply";
    }

    /**
     * Converts a CSV row string into a Reply object.
     *
     * @param row a single row from the CSV file
     * @return a Reply object constructed from the CSV data
     */
    @Override
    public Reply fromCSVRow(String row) {
        return (Reply) new Reply("", "", null, "", "").fromCSVRow(row);
    }

}
