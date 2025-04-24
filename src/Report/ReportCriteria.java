package Report;

import Enumerations.MaritalStatus;

/**
 * Represents a set of filtering criteria used for generating BTO project application reports.
 * Criteria include marital status, flat type, age range, and neighborhood.
 */
public class ReportCriteria {
    private MaritalStatus maritalStatus;
    private String flatType;
    private Integer minAge;
    private Integer maxAge;
    private String neighborhood;

    /**
     * Constructs an empty {@code ReportCriteria} with all fields set to null.
     */
    public ReportCriteria() {
    }

    /**
     * Constructs a {@code ReportCriteria} with the specified values.
     *
     * @param maritalStatus the marital status to filter by (e.g., SINGLE or MARRIED)
     * @param flatType      the flat type to filter by (e.g., "2-Room", "3-Room")
     * @param minAge        the minimum age for filtering applicants
     * @param maxAge        the maximum age for filtering applicants
     * @param neighborhood  the neighborhood to filter projects by
     */
    public ReportCriteria(MaritalStatus maritalStatus, String flatType, Integer minAge, Integer maxAge, String neighborhood) {
        this.maritalStatus = maritalStatus;
        this.flatType = flatType;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.neighborhood = neighborhood;
    }

    /**
     * Sets the age range filter. If the values are in reverse order, they will be swapped.
     *
     * @param minAge the minimum age
     * @param maxAge the maximum age
     */
    public void setAgeRange(Integer minAge, Integer maxAge) {
        if (minAge != null && maxAge != null && minAge > maxAge) {
            // Swap values if reversed
            this.minAge = maxAge;
            this.maxAge = minAge;
        } else {
            this.minAge = minAge;
            this.maxAge = maxAge;
        }
    }

    /**
     * Gets the marital status filter.
     *
     * @return the marital status
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the marital status filter.
     *
     * @param maritalStatus the marital status
     */
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * Gets the flat type filter.
     *
     * @return the flat type
     */
    public String getFlatType() {
        return flatType;
    }

    /**
     * Sets the flat type filter.
     *
     * @param flatType the flat type
     */
    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    /**
     * Gets the minimum age filter.
     *
     * @return the minimum age
     */
    public Integer getMinAge() {
        return minAge;
    }

    /**
     * Sets the minimum age filter.
     *
     * @param minAge the minimum age
     */
    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    /**
     * Gets the maximum age filter.
     *
     * @return the maximum age
     */
    public Integer getMaxAge() {
        return maxAge;
    }

    /**
     * Sets the maximum age filter.
     *
     * @param maxAge the maximum age
     */
    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * Gets the neighborhood filter.
     *
     * @return the neighborhood
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Sets the neighborhood filter.
     *
     * @param neighborhood the neighborhood
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }


}
