package Officer;

import Abstract.IEntity;
import Abstract.IUserProfile;
import Applicant.Applicant;
import Enumerations.MaritalStatus;

import User.User;
import Utils.CsvUtils;

/**
 * Represents an HDB Officer in the system.
 * Implements both IEntity and IUserProfile for persistence and profile handling.
 * Encapsulates officer details such as name, NRIC, age, marital status, and user credentials.
 */
public class Officer implements IEntity, IUserProfile {
    private User userProfile;
    private String name;
    private int age;
    private MaritalStatus maritalStatus;
    private String Nric;

    /**
     * Constructs an Officer with the specified attributes.
     *
     * @param name           the name of the officer
     * @param age            the age of the officer
     * @param maritalStatus  the marital status of the officer
     * @param Nric           the NRIC (unique ID) of the officer
     */
    public Officer(String name, int age, MaritalStatus maritalStatus, String Nric) {
        this.name = name;
        this.Nric = Nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }


    /**
     * Converts the Officer instance into a CSV row.
     *
     * @return a CSV string representation of the Officer
     */
    @Override
    public String toCSVRow() {
        String normalisedStatus = CsvUtils.capitalizeFirstLetter(this.maritalStatus.toString());
        return name + "," + Nric + "," + age + "," + normalisedStatus;
    }

    /**
     * Parses a CSV row into an Applicant object.
     * (Note: This may be an implementation issue — consider returning an Officer instead.)
     *
     * @param row the CSV string
     * @return an Applicant object parsed from the CSV row
     */
    @Override
    public Applicant fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        String nric = values[1];
        return new Applicant(values[0], age, status, nric);
    }

    /** @return the officer's NRIC (ID) */
    @Override
    public String getID() {
        return Nric;
    }

    /** @return the officer's name */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the officer's name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the officer's NRIC */
    @Override
    public String getNric() {
        return Nric;
    }

    /**
     * Sets the NRIC and updates the linked User profile's NRIC.
     *
     * @param nric the new NRIC
     */
    public void setNric(String nric) {
        this.userProfile.setNric(nric);
        this.Nric = nric;
    }

    /** @return the officer's age */
    @Override
    public int getAge() {
        return age;
    }

    /**
     * Sets the officer's age.
     *
     * @param age the new age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /** @return the officer's marital status */
    @Override
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the officer's marital status.
     *
     * @param maritalStatus the new marital status
     */
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /** @return the password from the associated user profile */
    @Override
    public String getPassword() {
        return this.userProfile.getPassword();
    }

    /**
     * Sets the password in the associated user profile.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.userProfile.setPassword(password);
    }

    /** @return the linked user profile object */
    public User getUserProfile() {
        return userProfile;
    }

    /**
     * Sets the associated user profile.
     *
     * @param userProfile the user profile to associate
     */
    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Returns a string representation of the officer.
     *
     * @return string containing officer details
     */
    @Override
    public String toString() {
        return "Applicant [name=" + name + ", NRIC=" + Nric
                + ", Age=" + age + ", MaritalStatus=" + maritalStatus.toString() + "]";
    }
}
