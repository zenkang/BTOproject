package Applicant;

import Abstract.IEntity;
import Abstract.IUserProfile;
import Enumerations.Role;
import User.User;
import Enumerations.MaritalStatus;
import Utils.CsvUtils;

/**
 * Represents an Applicant in the system.
 * Implements IEntity and IUserProfile interfaces.
 * An applicant contains personal details like name, age, marital status and NRIC.
 */
public class Applicant implements IEntity, IUserProfile {
    private User userProfile;
    private String name;
    private int age;
    private MaritalStatus maritalStatus;
    private String Nric;

    /**
     * Constructs an Applicant object with the specified personal details.
     * The password is managed separately in the User.csv via the User object.
     *
     * @param name
     * @param age
     * @param maritalStatus
     * @param Nric
     */
    public Applicant(String name, int age, MaritalStatus maritalStatus, String Nric) {
        this.name = name;
        this.Nric = Nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }


    /**
     * Converts this Applicant object into a CSV-formatted string.
     * The marital status is normalized with the first letter capitalized.
     *
     * @return a string representation of the applicant's data in CSV format
     */
    @Override
    public String toCSVRow() {
        String normalisedStatus = CsvUtils.capitalizeFirstLetter(this.maritalStatus.toString());
        return name + "," + Nric + "," + age + "," + normalisedStatus;
    }

    /**
     * Creates an Applicant object from a CSV-formatted string.
     * Assumes the CSV row contains name, NRIC, age, and marital status (in that order).
     *
     * @param row the CSV string representing an applicant
     * @return a new Applicant object constructed from the CSV data
     */
    @Override
    public Applicant fromCSVRow(String row) {
        String[] values = row.split(",");
        int age = Integer.parseInt(values[2]);
        MaritalStatus status = MaritalStatus.valueOf(values[3].toUpperCase());
        String nric = values[1];
        return new Applicant(values[0], age, status, nric);
    }

    /**
     * Gets the unique identifier (NRIC) of this applicant.
     *
     * @return the applicant's NRIC
     */
    @Override
    public String getID() {
        return Nric;
    }

    /**
     * Gets the full name of this applicant.
     *
     * @return the applicant's name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the applicant.
     *
     * @param name the name to set for the applicant
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the NRIC of this applicant.
     *
     * @return the applicant's NRIC
     */
    @Override
    public String getNric() {
        return Nric;
    }

    /**
     * Sets the NRIC of this applicant.
     * This method also updates the NRIC in the linked User profile.
     *
     * @param nric the NRIC to set
     */
    public void setNric(String nric) {
        this.userProfile.setNric(nric);
        this.Nric = nric;
    }

    /**
     * Gets the age of this applicant.
     *
     * @return the applicant's age
     */
    @Override
    public int getAge() {
        return age;
    }

    /**
     * Sets the age of this applicant.
     *
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the marital status of this applicant.
     *
     * @return the applicant's marital status
     */
    @Override
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the marital status of this applicant.
     *
     * @param maritalStatus the marital status to set
     */
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Gets the password of the applicant from the linked user profile
     *
     * @return the password of the user
     */
    @Override
    public String getPassword() {
        return this.userProfile.getPassword();
    }

    /**
     * Sets the password of the applicant by updating the linked user profile.
     *
     * @param password the new password to set
     */
    public void setPassword(String password) {
        this.userProfile.setPassword(password);
    }

    /**
     * Gets the User object associated with this applicant.
     *
     * @return the user profile of the applicant
     */
    public User getUserProfile() {
        return userProfile;
    }

    /**
     * Sets the User object associated with this applicant.
     *
     * @param userProfile the User object to associate with the applicant
     */
    public void setUserProfile(User userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Returns a string representation of the applicant,
     * including name, NRIC, age, and marital status.
     *
     * @return a string describing the applicant's profile
     */
    @Override
    public String toString() {
        return "Applicant [name=" + name + ", NRIC=" + Nric
                + ", Age=" + age + ", MaritalStatus=" + maritalStatus.toString() + "]";
    }
}
