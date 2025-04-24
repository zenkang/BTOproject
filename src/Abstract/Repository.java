package Abstract;



import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.ArrayList;

/**
 * Generic abstract repository class to manage storage, retrieval, and persistence
 * of entities that implement the IEntity interface. This class supports
 * operations such as loading from CSV, saving to CSV, filtering, and ID-based access.
 *
 * @param <T> the type of entity managed by this repository, must implement IEntity
 */
public abstract class Repository <T extends IEntity>{
    protected ArrayList<T> entities;
    private String filePath;
//    private HashMap<String, CacheEntry> LRUCache;

    /**
     * Constructs the repository and loads existing entities from a CSV file.
     *
     * @param filePath the path to the CSV file
     */
    public Repository(String filePath){
        this.filePath = filePath;
        boolean result = this.load();
//        this.LRUCache = new HashMap<>();
    }

    /**
     * Converts a CSV row string into an entity instance.
     * Must be implemented by subclasses.
     *
     * @param row the CSV-formatted row
     * @return the parsed entity instance
     */
    public abstract T fromCSVRow(String row);

    /**
     * Returns the header line for the CSV file.
     *
     * @return the CSV header string
     */
    public abstract String CSVHeader();

    /**
     * Loads entities from the CSV file into the in-memory list.
     * Skips malformed or empty lines.
     *
     * @return true if loading succeeds, false otherwise
     */
    private boolean load(){
        entities = new ArrayList<T>();
        try (BufferedReader br = new BufferedReader(new FileReader(this.filePath))) {
            String line;

            // Read the header line (optional)
            br.readLine(); // Skip the header (NRIC, Password, Role)

            // Read each subsequent line
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    T t = this.fromCSVRow(line);
                    if (t != null) {  // Only add if t is not null
                        entities.add(t);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                    e.printStackTrace();
                    // Continue to next line instead of failing entire load
                    continue;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Persists all current entities to the CSV file.
     *
     * @return true if storing succeeds, false otherwise
     */
    public boolean store(){
        // open file stream
        // write the headers
        // for each user: write the csv row using toCSVRow(), then go next line
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            // Write the updated data back, row by row
            // write the header
            bw.write(this.CSVHeader());
            bw.newLine();
            for (T t : entities) {
                String writable = t.toCSVRow();
                bw.write(writable);
                bw.newLine();  // Add a new line after each row
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves an entity by its unique ID.
     *
     * @param id the ID of the entity to retrieve
     * @return the entity if found, null otherwise
     */
    public T getByID(String id){
        // check if entry is in our LRU cache
//        if (this.LRUCache.containsKey(id)) {
//            System.out.println("Cache hit!");
//            System.out.println(this.LRUCache);
//            return this.entities.get(this.LRUCache.get(id).idx);
//        }
//        for (Integer i=0; i<entities.size(); i++){
//            T t = this.entities.get(i);
//            if (t.getID().equals(id)) {
                // update the cache
//                this.LRUCache.put(t.getID(), new CacheEntry(i, LocalDateTime.now()));
//                System.out.println("Cache miss, updated");
//                System.out.println(this.LRUCache);
//                return this.entities.get(i);
//            }
//        }
        for (T t : entities) {
            if (t.getID().equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Displays all entities using their toString representation.
     */
    public void display(){
        for (T t : entities) {
            System.out.println(t.toString());
        }
    }

    /**
     * Creates a new entity in the repository.
     * Fails if the entity is null or already exists.
     *
     * @param object the entity to create
     * @return true if creation succeeds, false otherwise
     */
    public boolean create(T object){
        // check null obj
        if (object==null)
            return false;
        // check existence
        if (this.getByID(object.getID())!=null)
            return false;
        entities.add(object);
        this.store();
        // update the cache
        //this.LRUCache.put(object.getID(), new CacheEntry(entities.size()-1, LocalDateTime.now()));
        return true;
    }

    /**
     * Deletes the specified entity from the repository.
     *
     * @param object the entity to delete
     * @return true if deletion succeeds, false otherwise
     */
    public boolean delete(T object){
        if (object==null)
            return false;
        entities.remove(object);
//        this.LRUCache.clear();
        this.store();
        return true;
    }

    private int findIndex(T object){
        int idx = -1;
//        if (this.LRUCache.containsKey(object.getID())) {
//            System.out.println("Cache hit!");
//            System.out.println(this.LRUCache);
//            return this.LRUCache.get(object.getID()).idx;
//        }
        for (int i = 0; i < entities.size(); i++) {
            if (object.getID().equals(entities.get(i).getID())) {
                idx = i;
                break;
            }
        }
//        System.out.println(idx);
//        System.out.println("Cache miss, updated");
//        this.LRUCache.put(object.getID(), new CacheEntry(idx,LocalDateTime.now()));
//        System.out.println(this.LRUCache);
        return idx;
    }

    /**
     * Updates an existing entity in the repository.
     *
     * @param object the entity to update
     * @return true if update succeeds, false otherwise
     */
    public boolean update(T object){
        if (object==null)
            return false;
        int index = findIndex(object);
        if (index==-1)
            return false;
        entities.set(index, object);
        this.store();
        return true;
    }

    /**
     * Retrieves all entities matching a specific filter.
     *
     * @param predicate The predicate to filter entities.
     * @return A list of entities matching the filter.
     */
    public List<T> getByFilter(Predicate<T> predicate) {
        return this.entities.stream().filter(predicate).toList();
    }
    /**Example
     *      gets list of enquiries with enquiry.applicantID == applicant object id
     *
     *     public static List<Enquiry> getEnquires(Applicant applicant){
     *         EnquiryRepository repo = getEnquiryRepository();
     *         return repo.getByFilter((Enquiry enquiry) -> record.getApplicantID().equals(applicant.getID()));
     *     }
     */
    protected String getLastId() {
        if (entities == null || entities.isEmpty()) {
            return "0";
        }
        int lastEntry = entities.size() - 1;
        if (lastEntry < 0) return "0"; // Edge case for empty CSV
        if (entities.get(lastEntry) == null) return "0";
        return entities.get(lastEntry).getID();
    }

    /**
     * Generates a new ID based on the most recent one and the specified prefix.
     *
     * @param defaultprefix the prefix for the ID (e.g., "EN" for Enquiry)
     * @return the newly generated ID
     */
    public String generateId(String defaultprefix) {
        String lastId = this.getLastId();
        if(lastId=="0"||lastId.isEmpty()){
            return String.format("%s%03d", defaultprefix, 1);
        }
        int i;
        for (i = 0; i < lastId.length(); i++) {
            if (Character.isDigit(lastId.charAt(i))) {
                break;
            }
        }
        String prefix = lastId.substring(0, i);
        int number = Integer.parseInt(lastId.substring(i));
        number++;
        return String.format("%s%03d", prefix, number);
    }

    /**
     * Checks if the repository has no entities.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return entities.isEmpty();
    }

    /**
     * Retrieves all entities currently stored in memory.
     *
     * @return an ArrayList of all entities
     */
    public ArrayList<T> getAll() {
        return this.entities;
    }


}
