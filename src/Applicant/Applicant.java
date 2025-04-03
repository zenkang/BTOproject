package Applicant;

import Abstract.IEntity;
import Enumerations.Role;
import User.User;
import Enumerations.MaritalStatus;
import Utils.CsvUtils;

public class Applicant implements IEntity {
    private User userProfile;
    private String name;
    private int age;
    private MaritalStatus maritalStatus; // Changed from String to MaritalStatus

    public Applicant(String name, String nric, int age, MaritalStatus maritalStatus, String password) {
        this.name = name;
        this.userProfile = new User(nric, password, Role.APPLICANT);
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    public Applicant(String name, int age, MaritalStatus maritalStatus, User userProfile) {
        this.name = name;
        this.userProfile = userProfile;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }




    @Override
    public String toCSVRow() {
        String normalisedStatus = CsvUtils.capitalizeFirstLetter(this.maritalStatus.toString());
        return name + "," + userProfile.getNric() + "," + age + "," + normalisedStatus + "," + userProfile.getPassword();
    }

    @Override
    public Applicant fromCSVRow(String row) {
        String[] values = row.split(",");
        Integer age = Integer.parseInt(values[2]);
        // Convert string back to enum; adjust case if needed
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        return new Applicant(values[0],values[1],age,status,values[4]);
    }

    @Override
    public String getID() {
        return userProfile.getID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNric() {
        return userProfile.getNric();
    }

    public void setNric(String nric) {
        this.userProfile.setNric(nric);
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

    public String getPassword() {
        return this.userProfile.getPassword();
    }

    public void setPassword(String password) {
        this.userProfile.setPassword(password);
    }

    public String toString() {
        return "Applicant [name=" + name + ", NRIC=" + this.userProfile.getNric() + ", Age=" + age + ", MaritalStatus=" + maritalStatus.toString() + ", Password=" + this.userProfile.getPassword() + "]";
    }
}
