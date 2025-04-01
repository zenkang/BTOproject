package Applicant;

import Abstract.IEntity;
import User.User;

public class Applicant implements IEntity {
    private User userProfile;
    private String name;
    private int age;
    private String maritalStatus;

    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.userProfile = new User(nric, password, "Applicant");
        this.age = age;
        this.maritalStatus = maritalStatus;
    }




    @Override
    public String toCSVRow() {
        return name + "," + userProfile.getNric() + "," + age + "," + maritalStatus + "," + userProfile.getPassword();
    }

    @Override
    public Applicant fromCSVRow(String row) {
        String[] values = row.split(",");
        Integer age = Integer.parseInt(values[2]);
        return new Applicant(values[0],values[1],age,values[3],values[4]);
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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getPassword() {
        return this.userProfile.getPassword();
    }

    public void setPassword(String password) {
        this.userProfile.setPassword(password);
    }

    public String toString() {
        return "Applicant [name=" + name + ", NRIC=" + this.userProfile.getNric() + ", Age=" + age + ", MaritalStatus=" + maritalStatus + ", Password=" + this.userProfile.getPassword() + "]";
    }
}
