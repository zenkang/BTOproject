package Project;

import Abstract.IEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Manager.ManagerController;
import Utils.CsvUtils;

/**
 * Represents a BTO housing project with various room types, unit counts, pricing,
 * application periods, and assigned officers.
 * Implements IEntity to support CSV serialization and repository operations.
 */
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
    private List<String> officer;
    private boolean visibility;

    /**
     * Returns the visibility status.
     *
     * @return {@code true} if the item is visible; {@code false} otherwise
     */
    public boolean isVisibility() {
        return visibility;
    }

    /**
     * Sets the visibility status.
     *
     * @param visibility {@code true} to make the item visible; {@code false} to hide it
     */
    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * Constructs a Project with full officer list.
     *
     * @param ID                 project ID
     * @param projectName        project name
     * @param neighbourhood      location of the project
     * @param type1              first room type
     * @param noOfUnitsType1     number of units for type1
     * @param sellPriceType1     price for type1
     * @param type2              second room type
     * @param noOfUnitsType2     number of units for type2
     * @param getSellPriceType2  price for type2
     * @param appDateOpen        application start date
     * @param appDateClose       application end date
     * @param managerID          manager assigned
     * @param noOfficersSlots    maximum officers allowed
     * @param officer            list of officers assigned
     * @param visible            project visibility status
     */
    public Project(String ID, String projectName, String neighbourhood, String type1, int noOfUnitsType1,
                   double sellPriceType1, String type2, int noOfUnitsType2, double getSellPriceType2,
                   LocalDate appDateOpen, LocalDate appDateClose, String managerID, int noOfficersSlots,
                   List<String> officer, boolean visible) {
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
        if (LocalDate.now().isAfter(appDateClose) || (noOfUnitsType1<=0 && noOfUnitsType2<=0) ) {
            this.visibility = false;
        }
        else {
            this.visibility=visible;
        }
    }

    /**
     * Constructs a Project without officer list (initially empty).
     */
    public Project(String ID, String projectName, String neighbourhood, String type1, int noOfUnitsType1,
                   double sellPriceType1, String type2, int noOfUnitsType2, double getSellPriceType2,
                   LocalDate appDateOpen, LocalDate appDateClose, String managerID, int noOfficersSlots,
                    boolean visible) {
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
        this.officer = new ArrayList<>(noOfficersSlots);
        this.noOfficersSlots=noOfficersSlots;
        if (LocalDate.now().isAfter(appDateClose) || (noOfUnitsType1<=0 && noOfUnitsType2<=0) ) {
            this.visibility = false;
        }
        else {
            this.visibility=visible;
        }
    }

    /**
     * Returns the unique ID of the project.
     *
     * @return the project ID
     */
    @Override
    public String getID() {
        return projectID;
    }

    /**
     * Returns the name of the project.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns the neighbourhood where the project is located.
     *
     * @return the project neighbourhood
     */
    public String getNeighbourhood() {
        return neighbourhood;
    }

    /**
     * Returns the name or identifier of the first unit type offered in the project.
     *
     * @return the first unit type
     */
    public String getType1() {
        return type1;
    }

    /**
     * Returns the number of units available for the first unit type.
     *
     * @return the number of units for type 1
     */
    public int getNoOfUnitsType1() {
        return noOfUnitsType1;
    }

    /**
     * Returns the selling price for the first unit type.
     *
     * @return the selling price for type 1
     */
    public double getSellPriceType1() {
        return sellPriceType1;
    }

    /**
     * Returns the name or identifier of the second unit type offered in the project.
     *
     * @return the second unit type
     */
    public String getType2() {
        return type2;
    }

    /**
     * Returns the number of units available for the second unit type.
     *
     * @return the number of units for type 2
     */
    public int getNoOfUnitsType2() {
        return noOfUnitsType2;
    }

    /**
     * Returns the selling price for the second unit type.
     *
     * @return the selling price for type 2
     */
    public double getSellPriceType2() {
        return sellPriceType2;
    }

    /**
     * Returns the opening date for applications to this project.
     *
     * @return the application opening date
     */
    public LocalDate getAppDateOpen() {
        return appDateOpen;
    }

    /**
     * Returns the closing date for applications to this project.
     *
     * @return the application closing date
     */
    public LocalDate getAppDateClose() {
        return appDateClose;
    }

    /**
     * Returns the ID of the manager assigned to this project.
     *
     * @return the manager ID
     */
    public String getManagerID() {
        return managerID;
    }

    /**
     * Returns the number of officer slots available for the project.
     *
     * @return the number of officer slots
     */
    public int getNoOfficersSlots() {
        return noOfficersSlots;
    }

    /**
     * Returns the list of officer IDs assigned to this project.
     *
     * @return the list of officer IDs
     */
    public List<String> getOfficer() {
        return officer;
    }

    /** @return CSV-compatible row string of this project */
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

    /**
     * Converts a CSV row string into a Project object.
     * @param row comma-separated row
     * @return constructed Project instance
     */
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
        List<String> officer = new ArrayList<String>(Arrays.asList(values[13].split(";")));
        boolean visible = Boolean.parseBoolean(values[14].trim());
        return new Project(values[0],values[1], values[2],values[3],noOfUnitsType1,
                sellPriceType1,values[6],noOfUnitsType2,sellPriceType2,
                openDate,closeDate,values[11],noOfficersSlots,
                officer,visible);
    }


    /**
     * Returns a string representation of the project containing all its core details,
     * including unit types, prices, application dates, manager, and officers.
     *
     * @return a formatted string of project information
     */
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

    /**
     * Sets the unique ID for the project.
     *
     * @param projectID the project ID to set
     */
    public void setProjectID(String projectID){
        this.projectID = projectID;
    }

    /**
     * Sets the name of the project.
     *
     * @param name the project name
     */
    public void setProjectName(String name){
        this.projectName = name;
    }

    /**
     * Sets the neighbourhood in which the project is located.
     *
     * @param newProjectNeighbourhood the neighbourhood name
     */
    public void setProjectNeighbourhood(String newProjectNeighbourhood) {
        this.neighbourhood = newProjectNeighbourhood;
    }


    /**
     * Sets the name of the first room type in the project.
     *
     * @param roomType1 the first room type
     */
    public void setProjectRoomType1(String roomType1){
        this.type1 = roomType1;
    }

    /**
     * Sets the number of units for the first room type.
     *
     * @param numOfType1 the unit count for type 1
     */
    public void setProjectNumOfType1(int numOfType1){
        this.noOfUnitsType1 = numOfType1;
    }

    /**
     * Sets the selling price for the first room type.
     *
     * @param sellPriceType1 the price for type 1
     */
    public void setSellPriceType1(double sellPriceType1){
        this.sellPriceType1=sellPriceType1;
    }

    /**
     * Sets the name of the second room type in the project.
     *
     * @param roomType2 the second room type
     */
    public void setProjectRoomType2(String roomType2){
        this.type2= roomType2;
    }

    /**
     * Sets the number of units for the second room type.
     *
     * @param numOfType2 the unit count for type 2
     */
    public void setProjectNumOfType2(int numOfType2){
        this.noOfUnitsType2 = numOfType2;
    }

    /**
     * Sets the selling price for the second room type.
     *
     * @param sellPriceType2 the price for type 2
     */
    public void setSellPriceType2(double sellPriceType2){
        this.sellPriceType2=sellPriceType2;
    }

    /**
     * Sets the date when applications for the project open.
     *
     * @param openDate the opening date
     */
    public void setProjectApplicationOpenData(LocalDate openDate){
        this.appDateOpen=openDate;
    }

    /**
     * Sets the date when applications for the project close.
     *
     * @param closeDate the closing date
     */
    public void setProjectApplicationCloseDate(LocalDate closeDate){
        this.appDateClose = closeDate;
    }

    /**
     * Sets the manager ID for this project.
     *
     * @param manager the manager's ID
     */
    public void setProjectManager(String manager){
        this.managerID = manager;
    }

    /**
     * Sets the number of officer slots for the project.
     *
     * @param numOfOfficers the number of officer slots
     */
    public void setProjectNumOfOfficers(int numOfOfficers){
        this.noOfficersSlots = numOfOfficers;
    }

    /**
     * Sets the list of officer IDs assigned to this project.
     *
     * @param array a list of officer IDs
     */
    public void setOfficer(List<String> array) {
        this.officer = array;
    }

    /**
     * Displays all project details for managerial review.
     */
    public void prettyPrintManager() {
        System.out.println("\nProject ID: " + this.getID());
        System.out.println("============================");
        System.out.println("Project name: " + this.getProjectName());
        System.out.println("Neighbourhood: " + this.getNeighbourhood());
        System.out.println("Room Types : " + this.getType1() + " , " + this.getType2());
        System.out.println("Number of "+this.getType1()+" units: " + this.getNoOfUnitsType1());
        System.out.println("Selling price of "+this.getType1()+" :  $" + this.getSellPriceType1());
        System.out.println("Number of "+this.getType2()+" units: " + this.getNoOfUnitsType2());
        System.out.println("Selling price of "+this.getType2()+" :  $" + this.getSellPriceType2());
        System.out.println("Application Open Date: " + this.getAppDateOpen());
        System.out.println("Application Close Date: " + this.getAppDateClose());
        System.out.println("Manager-in-charge: " + this.getManagerID());
        System.out.println("Number of Officer Slot(s): " + this.getNoOfficersSlots());
        System.out.println("Officer(s) Assigned: ");
        List<String> officers = this.getOfficer();
        for (String officer : officers) {
            System.out.println(officer);
        }
        System.out.println("Active Project: " + this.isVisibility());
        System.out.println("------------------------");

    }

    /**
     * Displays project details suitable for a single applicant viewing only 2-room units.
     */
    public void prettyPrint4SingleApplicant(){
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd MMM yyyy");
        System.out.println("\nProject ID: " + this.getID());
        System.out.println("============================");
        System.out.println("Project name: " + this.getProjectName());
        System.out.println("Neighbourhood: " + this.getNeighbourhood());
        if (this.getType1().equalsIgnoreCase("2-room")){
            System.out.println("Number of "+this.getType1()+" units: " + this.getNoOfUnitsType1());
            System.out.println("Selling price of "+this.getType1()+" :  $" + this.getSellPriceType1());
        }
        if (this.getType2().equalsIgnoreCase("2-room")){
            System.out.println("Number of "+this.getType2()+" units: " + this.getNoOfUnitsType2());
            System.out.println("Selling price of "+this.getType2()+" :  $" + this.getSellPriceType2());
        }
        System.out.println("Application Open Date: " + this.getAppDateOpen().format(fmt1));
        System.out.println("Application Close Date: " + this.getAppDateClose().format(fmt1));
    }

    /**
     * Displays project details relevant to applicants interested in 3-room units.
     */
    public void prettyPrintApplicant3room(){
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd MMM yyyy");
        System.out.println("\nProject ID: " + this.getID());
        System.out.println("============================");
        System.out.println("Project name: " + this.getProjectName());
        System.out.println("Neighbourhood: " + this.getNeighbourhood());
        if (this.getType1().equalsIgnoreCase("3-room")){
            System.out.println("Number of "+this.getType1()+" units: " + this.getNoOfUnitsType1());
            System.out.println("Selling price of "+this.getType1()+" :  $" + this.getSellPriceType1());
        }
        if (this.getType2().equalsIgnoreCase("3-room")){
            System.out.println("Number of "+this.getType2()+" units: " + this.getNoOfUnitsType2());
            System.out.println("Selling price of "+this.getType2()+" :  $" + this.getSellPriceType2());
        }
        System.out.println("Application Open Date: " + this.getAppDateOpen().format(fmt1));
        System.out.println("Application Close Date: " + this.getAppDateClose().format(fmt1));
    }

    /**
     * Displays project details tailored for married applicants.
     */
    public void prettyPrint4MarriedApplicant(){
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd MMM yyyy");
        System.out.println("\nProject ID: " + this.getID());
        System.out.println("============================");
        System.out.println("Project name: " + this.getProjectName());
        System.out.println("Neighbourhood: " + this.getNeighbourhood());
        System.out.println("Room Types : " + this.getType1() + " , " + this.getType2());
        System.out.println("Number of "+this.getType1()+" units: " + this.getNoOfUnitsType1());
        System.out.println("Selling price of "+this.getType1()+" :  $" + this.getSellPriceType1());
        System.out.println("Number of "+this.getType2()+" units: " + this.getNoOfUnitsType2());
        System.out.println("Selling price of "+this.getType2()+" :  $" + this.getSellPriceType2());
        System.out.println("Application Open Date: " + this.getAppDateOpen().format(fmt1));
        System.out.println("Application Close Date: " + this.getAppDateClose().format(fmt1));
    }

    /**
     * Displays basic project information for officers.
     */
    public void prettyPrint4Officer(){
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd MMM yyyy");
        System.out.println("\nProject ID: "+this.getID());
        System.out.println("Project name: " + this.getProjectName());
        System.out.println("Neighbourhood: " + this.getNeighbourhood());
        System.out.println("Application Open Date: "+this.getAppDateOpen().format(fmt1));
        System.out.println("Application Close Date: "+this.getAppDateClose().format(fmt1));
        System.out.println("Number of "+this.getType1()+" units: " + this.getNoOfUnitsType1());
        System.out.println("Number of "+this.getType2()+" units: " + this.getNoOfUnitsType2());
        System.out.println("Active Project: " + this.isVisibility());
        System.out.println("------------------------");
    }
}

