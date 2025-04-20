package Officer;

import Abstract.IEntity;
import Abstract.IUserProfile;
import Applicant.Applicant;
import Enumerations.MaritalStatus;
import Enumerations.Role;
import User.User;
import Utils.CsvUtils;

public class Officer implements IEntity, IUserProfile {
    private User userProfile;
    private String name;
    private int age;
    private MaritalStatus maritalStatus;
    private String Nric;
    public Officer(String name, int age, MaritalStatus maritalStatus, String Nric) {
        this.name = name;
        this.Nric = Nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    // Optional: Retain the old constructor for compatibility if needed
    public Officer(String name, String nric, int age, MaritalStatus maritalStatus, String password) {
        this.name = name;
        this.userProfile = new User(nric, password, Role.OFFICER);
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    @Override
    public String toCSVRow() {
        String normalisedStatus = CsvUtils.capitalizeFirstLetter(this.maritalStatus.toString());
        return name + "," + Nric + "," + age + "," + normalisedStatus;
    }

    @Override
    public Applicant fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        String nric = values[1];
        return new Applicant(values[0], age, status, nric);
    }

    @Override
    public String getID() {
        return Nric;
    }
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getNric() {
        return Nric;
    }

    public void setNric(String nric) {
        this.userProfile.setNric(nric);
        this.Nric = nric;
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
    @Override
    public String getPassword() {
        return this.userProfile.getPassword();
    }

    public void setPassword(String password) {
        this.userProfile.setPassword(password);
    }

    public User getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "Applicant [name=" + name + ", NRIC=" + Nric
                + ", Age=" + age + ", MaritalStatus=" + maritalStatus.toString() + "]";
    }
}
