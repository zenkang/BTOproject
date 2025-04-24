package Abstract;



import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
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
    private static final int CACHE_CAPACITY = 100;  // or whatever size you like
    private final Map<String, T> lruCache =
            new LinkedHashMap<>(CACHE_CAPACITY, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, T> eldest) {
                    return size() > CACHE_CAPACITY;
                }
            };


    /**
     * Constructs the repository and loads existing entities from a CSV file.
     *
     * @param filePath the path to the CSV file
     */
    public Repository(String filePath){
        this.filePath = filePath;
        boolean result = this.load();
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
    public T getByID(String id) {
        String key = id.toLowerCase();
        // 1) Try cache
        T hit = lruCache.get(key);
        if (hit != null) return hit;
        // 2) Fallback to list scan
        for (T t : entities) {
            if (t.getID().equalsIgnoreCase(id)) {
                lruCache.put(key, t);
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
    public boolean create(T object) {
        if (object == null || getByID(object.getID()) != null) return false;
        entities.add(object);
        store();
        lruCache.put(object.getID().toLowerCase(), object);
        return true;
    }

    /**
     * Deletes the specified entity from the repository.
     *
     * @param object the entity to delete
     * @return true if deletion succeeds, false otherwise
     */
    public boolean delete(T object) {
        if (object == null) return false;
        entities.remove(object);
        store();
        lruCache.remove(object.getID().toLowerCase());
        return true;
    }

    /**
     * Finds the index of the specified object in the {@code entities} list
     * by comparing their IDs.
     *
     * @param object the object whose index is to be found; must implement a {@code getID()} method
     * @return the index of the object in the list if found; otherwise, returns -1
     */
    private int findIndex(T object){
        int idx = -1;
        for (int i = 0; i < entities.size(); i++) {
            if (object.getID().equals(entities.get(i).getID())) {
                idx = i;
                break;
            }
        }
        return idx;
    }

    /**
     * Updates an existing entity in the repository.
     *
     * @param object the entity to update
     * @return true if update succeeds, false otherwise
     */
    public boolean update(T object) {
        if (object == null) return false;
        int idx = findIndex(object);
        if (idx < 0) return false;
        entities.set(idx, object);
        store();
        lruCache.put(object.getID().toLowerCase(), object);
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

    /**
     * Retrieves the last ID from the list of entities.
     *
     * @return The last ID as a string.
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
