package Manager;

import Abstract.IEntity;
import Abstract.IUserProfile;
import Enumerations.MaritalStatus;
import Enumerations.Role;
import User.User;
import Utils.CsvUtils;

public class Manager implements IEntity, IUserProfile {
    private User userProfile;
    private String name;
    private int age;
    private MaritalStatus maritalStatus;
    private String Nric;

    public Manager(String value, int age, MaritalStatus status, String nric) {
        this.name = value;
        this.age = age;
        this.maritalStatus = status;
        this.Nric = nric;
    }

    @Override
    public String toString() {
        return "Manager [name=" + name + ", NRIC=" + Nric
                + ", Age=" + age + ", MaritalStatus=" + maritalStatus.toString() + "]";
    }

    @Override
    public String toCSVRow() {
        String normalisedStatus = CsvUtils.capitalizeFirstLetter(this.maritalStatus.toString());
        return name + "," + Nric + "," + age + "," + normalisedStatus;
    }

    @Override
    public Manager fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        String nric = values[1];
        return new Manager(values[0], age, status, nric);
    }

    @Override
    public String getID() {
        return Nric;
    }

    public User getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }
    @Override
    public String getNric() {return Nric;
    }

    public void setNric(String nric) {
        this.userProfile.setNric(nric);
        this.Nric = nric;
    }
    @Override
    public String getPassword() {
        return this.userProfile.getPassword();
    }

    public void setPassword(String password) {
        this.userProfile.setPassword(password);
    }
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    @Override
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
