package Project;

import Abstract.IEntity;
import Utils.CsvUtils;

public class Project implements IEntity {
    private String projectID;
    private String projectName;
    private String neighbourhood;
    private String type1;
    private int noOfUnitsType1;
    private int sellPriceType1;
    private String type2;
    private int noOfUnitsType2;
    private int sellPriceType2;
    private String appDateOpen;
    private String appDateClose;
    private String manager;
    private int noOfficersSlots;
    private String[] officer;

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    private boolean visibility;
    public Project(String ID,String projectName, String neighbourhood, String type1, int noOfUnitsType1,
                   int sellPriceType1, String type2, int noOfUnitsType2, int getSellPriceType2,
                    String appDateOpen, String appDateClose, String manager,int noOfficersSlots,
                   String[] officer, boolean visibile) {
        this.projectID = ID;
        this.projectName = projectName;
        this.neighbourhood = neighbourhood;
        this.type1 = type1;
        this.noOfUnitsType1 = noOfUnitsType1;
        this.sellPriceType1 = sellPriceType1;
        this.type2 = type2;
        this.noOfUnitsType2=noOfUnitsType2;
        this.sellPriceType2=getSellPriceType2;
        this.appDateOpen=appDateOpen;
        this.appDateClose=appDateClose;
        this.manager=manager;
        this.noOfficersSlots=noOfficersSlots;
        this.officer=officer;
        this.visibility=visibile;
    }

    @Override
    public String getID() {return projectID;}
    public String getProjectName() {
        return projectName;
    }
    public String getNeighbourhood() {
        return neighbourhood;
    }
    public String getType1() {
        return type1;
    }
    public int getNoOfUnitsType1(){
        return noOfUnitsType1;
    }
    public int getSellPriceType1() {
        return sellPriceType1;
    }
    public String getType2() {
        return type2;
    }
    public int getNoOfUnitsType2() {
        return noOfUnitsType2;
    }
    public int getSellPriceType2() {
        return sellPriceType2;
    }
    public String getAppDateOpen(){
        return appDateOpen;
    }
    public String getAppDateClose(){
        return appDateClose;}
    public String getManager() {
        return manager;
    }
    public int getNoOfficersSlots() {
        return noOfficersSlots;
    }
    public String[] getOfficer() {
        return officer;
    }

    @Override
    public String toCSVRow() {
        return String.join(",",
                projectID,
                projectName,
                neighbourhood,
                type1,
                String.valueOf(noOfUnitsType1),
                String.valueOf(sellPriceType1),
                type2,
                String.valueOf(noOfUnitsType2),
                String.valueOf(sellPriceType2),
                appDateOpen,
                appDateClose,
                manager,
                String.valueOf(noOfficersSlots),
                String.join(",", officer),
                String.valueOf(visibility)
        );
    }

    public Project fromCSVRow(String row) {
        String[] values = row.split(",");
        int noOfUnitsType1 = Integer.parseInt(values[4]);
        int sellPriceType1 = Integer.parseInt(values[5]);
        int noOfUnitsType2 = Integer.parseInt(values[7]);
        int sellPriceType2 = Integer.parseInt(values[8]);
        int noOfficersSlots = Integer.parseInt(values[11]);
        String[] officer = values[13].split(",");
        boolean visibility = Boolean.parseBoolean(values[14]);
        return new Project(values[0],values[1], values[1],values[2],noOfUnitsType1,
                            sellPriceType1,values[5],noOfUnitsType2,sellPriceType2,
                            values[8],values[9],values[10],noOfficersSlots,
                            officer,visibility);
    }



    @Override
    public String toString() {
        return " Project ID: '" + projectID+'\''+
                ", Project Name: " + projectName + '\'' +
                ", Neighbourhood: '" + neighbourhood + '\'' +
                ", Type 1: " + type1 + '\'' +
                ", Number Of Units of Type 1: " + noOfUnitsType1 +
                ", Selling Price of Type 1: " + sellPriceType1 +
                ", Type 2: " + type2 + '\'' +
                ", Number Of Units of Type 2: " + noOfUnitsType2 +
                ", Selling Price of Type 2: " + sellPriceType2 +
                ", Application Opening Date: " + appDateOpen + '\'' +
                ", Application Closing Date: " + appDateClose + '\'' +
                ", Manager: " + manager + '\'' +
                ", Number of Officer Slots: " + noOfficersSlots +
                ", Officer/s: " + String.join(",", officer)
                ;
    }
    public void setProjectID(String projectID){
        this.projectID = projectID;
    }
    public void setProjectName(String name){
        this.projectName = name;
    }
    public void setProjectNeighbourhood(String newProjectNeighbourhood) {
        this.neighbourhood = newProjectNeighbourhood;
    }
    public void setProjectRoomType1(String roomType1){
        this.type1 = roomType1;
    }
    public void setProjectNumOfType1(int numOfType1){
        this.noOfUnitsType1 = numOfType1;
    }
    public void setSellPriceType1(int sellPriceType1){
        this.sellPriceType1=sellPriceType1;
    }
    public void setProjectRoomType2(String roomType2){
        this.type2= roomType2;
    }
    public void setProjectNumOfType2(int numOfType2){
        this.noOfUnitsType2 = numOfType2;
    }
    public void setSellPriceType2(int sellPriceType2){
        this.sellPriceType2=sellPriceType2;
    }
    public void setProjectApplicationOpenData(String openDate){
        this.appDateOpen=openDate;
    }
    public void setProjectApplicationCloseDate(String closeDate){
        this.appDateClose = closeDate;
    }
    public void setProjectManager(String manager){
        this.manager = manager;
    }
    public void setProjectNumOfOfficers(int numOfOfficers){
        this.noOfficersSlots = numOfOfficers;
    }

}

