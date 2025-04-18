package Project;

import Abstract.IEntity;
import Utils.CsvUtils;

public class Project implements IEntity {
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

    public Project(String projectName, String neighbourhood, String type1, int noOfUnitsType1,
                   int sellPriceType1, String type2, int noOfUnitsType2, int getSellPriceType2,
                    String appDateOpen, String appDateClose, String manager,int noOfficersSlots,
                   String[] officer) {

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
    }

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
                String.join(",", officer)
        );
    }

    public Project fromCSVRow(String row) {
        String[] values = row.split(",");
        int noOfUnitsType1 = Integer.parseInt(values[3]);
        int sellPriceType1 = Integer.parseInt(values[4]);
        int noOfUnitsType2 = Integer.parseInt(values[6]);
        int sellPriceType2 = Integer.parseInt(values[7]);
        int noOfficersSlots = Integer.parseInt(values[10]);
        String[] officer = values[12].split(",");
        return new Project(values[0], values[1],values[2],noOfUnitsType1,
                            sellPriceType1,values[5],noOfUnitsType2,sellPriceType2,
                            values[8],values[9],values[10],noOfficersSlots,
                            officer);
    }

    @Override
    public String getID() {
        return projectName;
    }

    @Override
    public String toString() {
        return "Project Name: " + projectName + '\'' +
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
}
