package Project;

import Abstract.IEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Utils.CsvUtils;

public class Project implements IEntity {
    private String projectID;
    private String projectName;
    private String neighbourhood;
    private String type1;
    private int noOfUnitsType1;
    private double sellPriceType1;
    private String type2;
    private int noOfUnitsType2;
    private double sellPriceType2;
    private LocalDate appDateOpen;
    private LocalDate appDateClose;
    private String managerID;
    private int noOfficersSlots;
    private String[] officer;
    private boolean visibility;

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public Project(String ID, String projectName, String neighbourhood, String type1, int noOfUnitsType1,
                   double sellPriceType1, String type2, int noOfUnitsType2, double getSellPriceType2,
                   LocalDate appDateOpen, LocalDate appDateClose, String managerID, int noOfficersSlots,
                   String[] officer, boolean visible) {
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
        this.managerID = managerID;
        this.noOfficersSlots=noOfficersSlots;
        this.officer=officer;
        this.visibility=visible;
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
    public double getSellPriceType1() {
        return sellPriceType1;
    }
    public String getType2() {
        return type2;
    }
    public int getNoOfUnitsType2() {
        return noOfUnitsType2;
    }
    public double getSellPriceType2() {
        return sellPriceType2;
    }
    public LocalDate getAppDateOpen(){
        return appDateOpen;
    }
    public LocalDate getAppDateClose(){
        return appDateClose;}
    public String getManagerID() {
        return managerID;
    }
    public int getNoOfficersSlots() {
        return noOfficersSlots;
    }
    public String[] getOfficer() {
        return officer;
    }

    @Override
    public String toCSVRow() {
        String officersString = String.join(";", this.officer);
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
                CsvUtils.getFmtDate(appDateOpen),
                CsvUtils.getFmtDate(appDateClose),
                managerID,
                String.valueOf(noOfficersSlots),
                officersString,
                String.valueOf(visibility)
        );
    }

    public Project fromCSVRow(String row) {
        String[] values = row.split(",");
        int noOfUnitsType1 = Integer.parseInt(values[4].trim());
        double sellPriceType1 = Double.parseDouble(values[5].trim());
        int noOfUnitsType2 = Integer.parseInt(values[7].trim());
        double sellPriceType2 = Double.parseDouble(values[8].trim());
        int noOfficersSlots = Integer.parseInt(values[12].trim());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate openDate = LocalDate.parse(values[9], formatter);
        LocalDate closeDate = LocalDate.parse(values[10], formatter);
        String[] officer = values[13].split(";");
        boolean visible = Boolean.parseBoolean(values[14].trim());
        if (LocalDate.now().isAfter(appDateClose)) {
            visible = false;
        }
        return new Project(values[0],values[1], values[2],values[3],noOfUnitsType1,
                sellPriceType1,values[6],noOfUnitsType2,sellPriceType2,
                openDate,closeDate,values[11],noOfficersSlots,
                officer,visible);
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
                ", Application Opening Date: " + CsvUtils.getFmtDate(appDateOpen) + '\'' +
                ", Application Closing Date: " +  CsvUtils.getFmtDate(appDateClose) + '\'' +
                ", Manager: " + managerID + '\'' +
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
    public void setSellPriceType1(double sellPriceType1){
        this.sellPriceType1=sellPriceType1;
    }
    public void setProjectRoomType2(String roomType2){
        this.type2= roomType2;
    }
    public void setProjectNumOfType2(int numOfType2){
        this.noOfUnitsType2 = numOfType2;
    }
    public void setSellPriceType2(double sellPriceType2){
        this.sellPriceType2=sellPriceType2;
    }
    public void setProjectApplicationOpenData(LocalDate openDate){
        this.appDateOpen=openDate;
    }
    public void setProjectApplicationCloseDate(LocalDate closeDate){
        this.appDateClose = closeDate;
    }
    public void setProjectManager(String manager){
        this.managerID = manager;
    }
    public void setProjectNumOfOfficers(int numOfOfficers){
        this.noOfficersSlots = numOfOfficers;
    }

    public void setOfficer(String[] array) {
        this.officer = array;
    }
}

