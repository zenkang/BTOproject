package Manager;

import Abstract.IEntity;
import Enumerations.MaritalStatus;
import Enumerations.Role;
import User.User;
import Utils.CsvUtils;

public class Manager implements IEntity {
    private User userProfile;
    private String name;
    private int age;
    private MaritalStatus maritalStatus;
    public Manager(String value, int age, MaritalStatus status, User user) {
        this.name = value;
        this.age = age;
        this.maritalStatus = status;
        this.userProfile = user;
    }

    @Override
    public String toString() {
        return "Manager [name=" + name + ", NRIC=" + this.userProfile.getNric()
                + ", Age=" + age + ", MaritalStatus=" + maritalStatus.toString() + "]";
    }

    @Override
    public String toCSVRow() {
        String normalisedStatus = CsvUtils.capitalizeFirstLetter(this.maritalStatus.toString());
        return name + "," + userProfile.getNric() + "," + age + "," + normalisedStatus;
    }

    @Override
    public IEntity fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2].trim());
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        // Create a User with an empty password; actual password data is maintained in User.csv
        User user = new User(values[1], "", Role.MANAGER);
        return new Manager(values[0], age, status, user);
    }

    @Override
    public String getID() {
        return userProfile.getID();
    }

    public User getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }
    public String getNric() {
        return userProfile.getNric();
    }

    public void setNric(String nric) {
        this.userProfile.setNric(nric);
    }
    public String getPassword() {
        return this.userProfile.getPassword();
    }

    public void setPassword(String password) {
        this.userProfile.setPassword(password);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}
