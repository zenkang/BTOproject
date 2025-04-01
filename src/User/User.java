package User;

import Abstract.IEntity;

public class User implements IEntity {
    private String nric;
    private String password;
    private String role;

    public User(String nric, String password, String role) {
        this.nric = nric;
        this.password = password;
        this.role = role;
    }

    public String getNric() {
        return nric;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User [nric=" + nric + ", password=" + password + ", role=" + role + "]";
    }

    @Override
    public String toCSVRow() {
        return nric+","+password+","+role;
    }

    public User fromCSVRow(String row) {
        String[] values = row.split(",");
        return new User(values[0], values[1], values[2]);
    }

    @Override
    public String getID() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }
}
