package User;
import Abstract.Repository;
import Enumerations.Role;

import java.util.ArrayList;

public class UsersRepository extends Repository<User> {
    private static UsersRepository instance;
    private UsersRepository(String filePath) {
        super(filePath);
    }
    public static UsersRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new UsersRepository(filePath);
        }
        return instance;
    }

    @Override
    public User fromCSVRow(String row) {
        String[] values = row.split(",");
        Role role = Role.valueOf(values[2].toUpperCase());
        return new User(values[0], values[1], role);
    }

    @Override
    public String CSVHeader() {
        return "NRIC,PASSWORD,ROLE";
    }

    public ArrayList<User> getAllUsers() {
        return this.entities;
    }


    public void display(){
        for (User u : entities){
            System.out.println(u);
        }
    }
}
