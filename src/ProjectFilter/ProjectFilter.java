package ProjectFilter;

public class ProjectFilter {
    private String location;
    private String flatType; // e.g., "2-Room" or "3-Room"

    public ProjectFilter() {
        // defaults: no filter is applied (empty strings)
        this.location = "";
        this.flatType = "";
    }

    // Getters and setters
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getFlatType() {
        return flatType;
    }
    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }
}
