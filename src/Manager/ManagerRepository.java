package Manager;

import Abstract.Repository;
import Enumerations.MaritalStatus;
import Enumerations.Role;
import User.User;

public class ManagerRepository extends Repository<Manager> {
    private static ManagerRepository instance;
    private ManagerRepository(String filePath) {
        super(filePath);
    }
    public static ManagerRepository getInstance(String filePath) {
        if (instance == null) {
            instance = new ManagerRepository(filePath);
        }
        return instance;
    }

    @Override
    public Manager fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2].trim());
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        // Create a User with an empty password; actual password is managed in User.csv
        User user = new User(values[1], "", Role.MANAGER);
        return new Manager(values[0], age, status, user);
    }

    @Override
    public String CSVHeader() {
         return "Name,NRIC,Age,Marital Status";
    }
}
