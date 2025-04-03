package User;
import Abstract.Repository;
import Enumerations.Role;

public class UsersRepository extends Repository<User> {

    public UsersRepository(String filePath) {
        super(filePath);
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



    public void display(){
        for (User u : entities){
            System.out.println(u);
        }
    }
}
