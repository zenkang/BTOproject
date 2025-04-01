package User;
import Abstract.Repository;

public class UsersRepository extends Repository<User> {

    public UsersRepository(String filePath) {
        super(filePath);
    }

    @Override
    public User fromCSVRow(String row) {
        String[] values = row.split(",");
        return new User(values[0], values[1], values[2]);
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
