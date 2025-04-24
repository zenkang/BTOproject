package Abstract;



import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.function.Predicate;
import java.util.ArrayList;

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



    public Repository(String filePath){
        this.filePath = filePath;
        boolean result = this.load();
    }

    public abstract T fromCSVRow(String row);

    public abstract String CSVHeader();

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


    public void display(){
        for (T t : entities) {
            System.out.println(t.toString());
        }
    }

    public boolean create(T object) {
        if (object == null || getByID(object.getID()) != null) return false;
        entities.add(object);
        store();
        lruCache.put(object.getID().toLowerCase(), object);
        return true;
    }

    public boolean delete(T object) {
        if (object == null) return false;
        entities.remove(object);
        store();
        lruCache.remove(object.getID().toLowerCase());
        return true;
    }

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
     * Retrieves the last ID from the list of entities.
     *
     * @return The last ID as a string.
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

    public boolean isEmpty() {
        return entities.isEmpty();
    }

    public ArrayList<T> getAll() {
        return this.entities;
    }


}
