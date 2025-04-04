package Abstract;

import java.io.*;
import java.util.ArrayList;

public abstract class Repository <T extends IEntity>{
    protected ArrayList<T> entities;
    private String filePath;

    public Repository(String filePath){
        this.filePath = filePath;
        boolean result = this.load();
        if (result) {
            System.out.println(filePath+" loaded successfully");
        }
        else {
            System.out.println(filePath+" could not be loaded");
        }
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

                T t = this.fromCSVRow(line);
                entities.add(t);

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

    public T getByID(String id){
        for (T t : entities) {
            if (t.getID().equalsIgnoreCase(id)) {
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

    public boolean create(T object){
        // check null obj
        if (object==null)
            return false;
        // check existence
        if (this.getByID(object.getID())!=null)
            return false;
        entities.add(object);
        this.store();
        return true;
    }

    public boolean delete(T object){
        if (object==null)
            return false;
        entities.remove(object);
        this.store();
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
}
