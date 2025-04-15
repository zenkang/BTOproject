package ProjectFilter;

public class ProjectFilter {


    private String neighbourhoodName;
    private String flatType; // e.g., "2-Room" or "3-Room"

    public ProjectFilter() {
        // defaults: no filter is applied (empty strings)
        this.neighbourhoodName = "";
        this.flatType = "";
    }

    // Getters and setters
    public String getNeighbourhoodName() {
        return neighbourhoodName;
    }
    public void setNeighbourhoodName(String neighbourhoodName) {
        this.neighbourhoodName = neighbourhoodName;
    }
    public String getFlatType() {
        return flatType;
    }
    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }
}
