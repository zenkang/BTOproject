package Report;

import Enumerations.MaritalStatus;

public class ReportCriteria {
    private MaritalStatus maritalStatus;
    private String flatType;
    private Integer minAge;
    private Integer maxAge;
    private String neighborhood;

    public ReportCriteria() {
    }

    public ReportCriteria(MaritalStatus maritalStatus, String flatType, Integer minAge, Integer maxAge, String neighborhood) {
        this.maritalStatus = maritalStatus;
        this.flatType = flatType;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.neighborhood = neighborhood;
    }

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

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getFlatType() {
        return flatType;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }


}
