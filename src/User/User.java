package User;

import Abstract.IEntity;
import Enumerations.Role;
import Utils.CsvUtils;

public class User implements IEntity {
    private String nric;
    private String password;
    private Role role;

    public User(String nric, String password, Role role) {
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
    public Role getRole() {
        return role;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User [nric=" + nric + ", password=" + password + ", role=" + role.toString() + "]";
    }

    @Override
    public String toCSVRow() {
        String normalisedRole = CsvUtils.capitalizeFirstLetter(this.role.toString());
        return nric+","+password+","+normalisedRole;
    }

    public User fromCSVRow(String row) {
        String[] values = row.split(",");
        Role role = Role.valueOf(values[2].toUpperCase());
        return new User(values[0], values[1], role);
    }

    @Override
    public String getID() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }
}
