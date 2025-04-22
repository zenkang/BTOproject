package Officer;



import Applicant.Applicant;
import Applicant.ApplicantController;
import Enquiry.Enquiry;
import Enquiry.EnquiryController;
import Enumerations.ApplicationStatus;
import Enumerations.MaritalStatus;
import Manager.ManagerController;
import Project.Project;
import ProjectApplication.ProjectApplication;
import ProjectRegistration.ProjectRegistration;
import ProjectRegistration.ProjectRegistrationController;
import Receipt.ReceiptController;
import Reply.Reply;
import Reply.ReplyController;
import User.UserBoundary;
import Utils.SafeScanner;
import Project.ProjectController;
import ProjectApplication.ProjectApplicationController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


import static Utils.RepositoryGetter.getProjectApplicationsRepository;

public class OfficerBoundary {
    private static Officer officer;
    public OfficerBoundary(Officer officer) {
        this.officer = officer;
    }
    public void displayMenu() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\n=== HDB Officer Menu ===");
            System.out.println("1. View/update my profile");
            System.out.println("2. View Projects");
            System.out.println("3. View projects I'm handling");
            System.out.println("4. Apply Project ");
            System.out.println("5. View my Application");
            System.out.println("6. Register to handle a project");
            System.out.println("7. View registration status");
            System.out.println("8. View Application Menu for handled Project");
            System.out.println("9. Generate flat booking receipt");
            System.out.println("10. View Enquiry Menu");
            System.out.println("11. Change Password");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 11);
            switch (choice) {
                case 1 -> viewOfficerProfile();
                case 2 -> displayProjectsForOfficer(officer);
                case 3 -> viewHandledProjects(officer);
                case 4 -> applyForProject(officer);
                case 5 -> viewApplication(officer);
                case 6 -> registerToHandleProject(officer,sc);
                case 7 -> viewRegistrationStatus(officer,sc);
                case 8 -> viewApplicantApplications(officer);
                case 9 -> generateBookingReceipt(officer,sc);
                case 10 -> officerEnquiryMenu(officer);
                case 11 -> changePassword();
                case 0 -> System.out.println("Exiting the Officer Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        }
        while (choice != 0 && choice !=11) ;
        if (choice == 0){
            sc.close();
        }
    }

    private void viewRegistrationStatus(Officer officer, Scanner sc) {
        List<ProjectRegistration> projectRegistrations = ProjectRegistrationController.getProjectRegistrationByOfficerId(officer.getID());
        System.out.println("\n=== Registration Status ===");
        if(!projectRegistrations.isEmpty()){
            for (ProjectRegistration projectRegistration : projectRegistrations) {
                System.out.println("Registration ID: " + projectRegistration.getRegistrationID());
                System.out.println("Project Name: " + projectRegistration.getProjectName());
                System.out.println("Officer ID: " + projectRegistration.getOfficerId());
                System.out.println("Status: " + projectRegistration.getStatus());
            }
        }
        else {
            System.out.println("You did not register for any project.");
        }
    }

    private void registerToHandleProject(Officer officer,Scanner sc) {

        List<Project> projects = ProjectController.getFilteredProjects(project -> project.isVisibility());
        if(!projects.isEmpty()){
            System.out.println("\n=== Projects ===");
            for (Project project : projects) {
                prettyPrintProject(project);
            }
        }
        else{
            System.out.println("No projects can be registered.");
        }
        List<String> validprojIDs = projects.stream().map(Project::getID).distinct().toList();
        String projectID = SafeScanner.getValidatedStringInput(sc,"Enter Project ID you want to apply for: ",validprojIDs);
        boolean canRegister = ProjectRegistrationController.canRegisterForProject(officer.getID(),projectID);
        boolean hasRegistered = ProjectRegistrationController.hasRegisteredForProject(officer.getID(),projectID);
        if(canRegister && !hasRegistered){
            if(!ProjectRegistrationController.createProjectRegistration(projectID.toUpperCase(),officer.getID())){
                System.out.println("Registration could not be created.");
            }
            else{
                System.out.println("Registration successful.");
            }
        }
        else{
            System.out.println("You are not allowed to register a project.");
        }
    }


    public void viewOfficerProfile() {
        Scanner sc = new Scanner(System.in);
        String selection;
        do {
            Utils.PrettyPrint.prettyPrint(officer);

            List<String> validOptions = Arrays.asList("y", "n");
            selection = SafeScanner.getValidatedStringInput(sc,"Would you like to update your profile?\nEnter: y/n\n",validOptions);
            if (selection.equals("y")) {
                updateOfficerProfile();
            }
        }while(!selection.equals("n"));

    }
    private void changePassword() {
        UserBoundary.changePassword(officer.getUserProfile());
    }
    public void updateOfficerProfile() {
        int choice;
        Scanner sc = new Scanner(System.in);
        do{
            Utils.PrettyPrint.prettyUpdate(officer);

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 3);

            switch (choice) {
                case 1 -> updateName();
                case 2 -> updateAge();
                case 3 -> updateMaritalStatus();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);

    }
    private void updateAge(){
        Scanner sc = new Scanner(System.in);
        int age;
        age = SafeScanner.getValidatedIntInput(sc, "Enter your new age: ", 0, 200);
        if(OfficerController.updateAge(officer, age)){
            System.out.println("Your age has been updated!\n");
        }
        else{
            System.out.println("Update failed, try again later\n");
        }
    }

    private void updateName(){
        Scanner sc = new Scanner(System.in);
        String newName = SafeScanner.getValidatedStringInput(sc,"Enter your new Name: ",50);
        if(OfficerController.updateName(officer, newName)){
            System.out.println("Your name has been updated!\n");
        }
        else{
            System.out.println("Update failed, try again later\n");
        }
    }

    private void updateMaritalStatus(){
        Scanner sc = new Scanner(System.in);
        List<String> validOptions = Arrays.asList("m", "s");
        String maritalStatus = SafeScanner.getValidatedStringInput(sc,"Enter your marital status: ( m: Married , s: Single)\n", validOptions);
        if(OfficerController.updateMaritalStatus(officer, maritalStatus)){
            System.out.println("Your marital status has been updated!\n");
        }
        else{
            System.out.println("Update failed, try again later\n");
        }
    }


    //Applicant Functionalities

    //Project
    private static void displayProjectsForOfficer(Officer officer)  {
        List<ProjectApplication> applications =
                ProjectApplicationController.getApplicationsByApplicantID(officer.getID());

        // 2) collect all the project‑IDs this applicant has already applied to
        Set<String> appliedIds = new HashSet<>();
        if (!applications.isEmpty()) {
            System.out.println("\nYou have applied to the following project"
                    + (applications.size() > 1 ? "s:" : ":"));
            for (ProjectApplication app : applications) {
                appliedIds.add(app.getProjectID());
                Project p = ProjectController.getProjectByID(app.getProjectID());
                prettyPrintOfficerProject(officer, p,false);
            }
        }

        // 3) fetch the whole “open” list, then skip anything in appliedIds
        List<Project> filteredProjects = ProjectController.getProjectsForApplicant(officer);
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects are open to your user group.");
        } else {
            System.out.println("========= Projects =========");
            for (Project proj : filteredProjects) {
                prettyPrintOfficerProject(officer, proj,false);
            }
        }
    }
    private void viewHandledProjects(Officer officer) {
        List<Project> filteredProjects = ProjectController.getProjectsHandledByOfficer(officer.getID());
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects handled by you.");
        }
        else{
            System.out.println("========= Projects =========");
            for (Project proj : filteredProjects) {
                prettyPrintOfficerProject(officer, proj,true);
            }
        }
    }

    public static void applyForProject(Officer officer) {
        Scanner sc = new Scanner(System.in);
        String roomType;
        if (!ProjectApplicationController.checkPreviousApplication(officer.getNric())) {
            System.out.println("You have already submitted an Application Before.");
            return;
        }
        if(officer.getAge() < 21){
            System.out.println("You must be older than 21 to apply.");
            return;
        }
        if(officer.getMaritalStatus() == MaritalStatus.SINGLE && officer.getAge()<35){
            System.out.println("Single applicants have to be 35 years old and above to apply.");
            return;
        }
        if(ProjectController.getProjectsForApplicant(officer).isEmpty()) {
            System.out.println("No Projects available for applications at the moment.");
        }

        else {
            System.out.println("\n=== Project Application ===");
            System.out.println("Valid Projects:");
            List<String> ValidprojIDs = ProjectController.getProjectIDsForApplicant(officer);
            ValidprojIDs.forEach(item -> System.out.println("ID: " + item));
            // TO BE UPDATED
            // BEST TO GET FILTERED VALID PROJECT ID BASED ON APPLICANT'S ATTRIBUTE
            //TO BE ADDED: CHECKS TO ENSURE NUM OF UNITS AVAILABLE FOR THE PROJ >1
            String projectID = SafeScanner.getValidatedStringInput(sc,"Enter Project ID you want to apply for: ",ValidprojIDs);
            boolean isProjectHandled = ProjectController.isHandlingProject(officer.getID(),projectID);
            if(isProjectHandled){
                System.out.println("You cannot apply for this project as you are handling this project.");
            }
            else{
                if(officer.getMaritalStatus() == MaritalStatus.SINGLE){
                    roomType = "2-room";
                }
                else {
                    List<String> validRoomTypes = Arrays.asList("2-room", "3-room");
                    roomType = SafeScanner.getValidatedStringInput(sc, "Enter a room type (2-Room or 3-Room): ", validRoomTypes);
                }

                if (!ProjectApplicationController.createProjectApplication(projectID.toUpperCase(),roomType, officer.getID())){
                    System.out.println("Project application could not be created.");
                }
                else{
                    System.out.println("Application created successfully!");
                }
            }


        }
    }

    //Project Application
    public static void viewApplication(Officer officer) {
        List<ProjectApplication> application = ProjectApplicationController.getApplicationsByApplicantID(officer.getID());
        if (application.isEmpty()){
            System.out.println("Application could not be found.");
        }
        else {
            application.forEach(
                    OfficerBoundary::prettyPrintProjectApplications
            );
        }
    }

    //Enquiry
    public static void officerEnquiryMenu(Officer officer) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Enquiry Menu (Officer) ---");
            System.out.println("1. Submit new enquiry");
            System.out.println("2. View my enquiries");
            System.out.println("3. Edit an existing enquiry");
            System.out.println("4. Delete an enquiry");
            System.out.println("5. View replies");
            System.out.println("6. View Handled Project Enquiries");
            System.out.println("7. Reply to Enquiry");
            System.out.println("0. Exit");
            choice  = SafeScanner.getValidatedIntInput(sc, "Enter option: ", 0, 7);

            switch (choice) {
                case 1 -> submitEnquiry(officer.getNric());
                case 2 -> viewEnquiries(officer.getNric());
                case 3 -> editEnquiry(officer.getNric());
                case 4 -> deleteEnquiry(officer.getNric());
                case 5 -> viewRepliedEnquiry(officer.getNric(), sc);
                case 6 -> displayHandledProjectEnquiries(officer.getID());
                case 7 -> replyToHandledProjectEnquiries(officer,sc);
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option.");
            }
        }while(choice !=0);
    }

    private static void displayHandledProjectEnquiries(String id) {
        List<Enquiry> enquiries = EnquiryController.getEnquiriesForHandledProject(id);
        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries.");
        } else {
            for (Enquiry e : enquiries) {
                System.out.println(e);
            }
        }
    }

    public static void submitEnquiry(String nric) {
        Scanner sc = new Scanner(System.in);
        String projectName = SafeScanner.getValidProjectName(sc);
        System.out.print("Enter your message: ");
        String message = sc.nextLine();
        LocalDate today = LocalDate.now();
        EnquiryController.addEnquiry(today,projectName,nric,message);

        System.out.println("Enquiry submitted!");
    }

    private static void viewRepliedEnquiry(String officerId, Scanner sc) {
        System.out.println("\n--- View Replied Enquiry ---");

        // Step 1: Get only enquiries for projects this officer handles
        List<Enquiry> enquiries = EnquiryController.getAllEnquiriesForHandledProjects(officerId);

        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries for the projects you are handling.");
            return;
        }

        // Step 2: Display all enquiries with their REPLIED or NOT YET REPLIED status
        System.out.println("Enquiries for your projects:");
        for (Enquiry e : enquiries) {
            String status = e.isReplied() ? "REPLIED" : "NOT YET REPLIED";
            System.out.println("Enquiry ID: " + e.getEnquiryId()
                    + " | Project: " + e.getProjectName()
                    + " | Applicant: " + e.getApplicantNric()
                    + " | Status: " + status);
        }

        // Step 3: Get valid enquiry IDs (case insensitive list)
        List<String> validIds = enquiries.stream()
                .map(e -> e.getEnquiryId().toLowerCase())
                .toList();

        // Step 4: Ask user to input Enquiry ID safely
        String enquiryIdInput = SafeScanner.getValidatedStringInput(sc,
                "\nEnter Enquiry ID to view replies: ", validIds);

        // Step 5: Normalize back to real case-sensitive Enquiry ID
        String finalEnquiryId = enquiries.stream()
                .map(Enquiry::getEnquiryId)
                .filter(enquiryId -> enquiryId.equalsIgnoreCase(enquiryIdInput))
                .findFirst()
                .orElse(enquiryIdInput); // fallback

        // Step 6: Retrieve and display replies
        List<Reply> replies = ReplyController.getRepliesByEnquiry(finalEnquiryId);

        if (replies.isEmpty()) {
            System.out.println("There are no replies for this enquiry.");
        } else {
            System.out.println("\n--- Replies for Enquiry ID: " + finalEnquiryId + " ---");
            for (Reply reply : replies) {
                printPrettyReply(reply);
            }
        }
    }



    public static void viewEnquiries(String nric) {
        List<Enquiry> enquiries = EnquiryController.getEnquiriesByApplicant(nric);
        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries.");
        } else {
            for (Enquiry e : enquiries) {
                System.out.println(e);
            }
        }
    }

    public static void editEnquiry(String nric) {
        Scanner sc = new Scanner(System.in);
        List<Enquiry> enquiries = EnquiryController.getEnquiriesByApplicant(nric);
        List<Enquiry> editable = enquiries.stream()
                .filter(e -> !e.isReplied())
                .collect(Collectors.toList());

        if (editable.isEmpty()) {
            System.out.println("No editable enquiries found. You cannot edit replied enquiries.");
            return;
        }

        System.out.println("Select an enquiry to edit:");
        for (int i = 0; i < editable.size(); i++) {
            System.out.println((i + 1) + ". " + editable.get(i));
        }

        int choice = SafeScanner.getValidatedIntInput(sc, "Choose: ", 1, editable.size());

        Enquiry enquiry = editable.get(choice - 1);
        System.out.print("Enter new message for enquiry: ");
        String newMessage = sc.nextLine();
        enquiry.setMessage(newMessage);

        EnquiryController.updateEnquiry(enquiry);
        System.out.println("Enquiry updated successfully!");
    }

    public static void deleteEnquiry(String nric) {
        Scanner sc = new Scanner(System.in);
        List<Enquiry> enquiries = EnquiryController.getEnquiriesByApplicant(nric);
        List<Enquiry> deletable = enquiries.stream()
                .filter(e -> !e.isReplied())
                .collect(Collectors.toList());

        if (deletable.isEmpty()) {
            System.out.println("No deletable enquiries found. You cannot delete replied enquiries.");
            return;
        }

        System.out.println("Select an enquiry to delete:");
        for (int i = 0; i < deletable.size(); i++) {
            System.out.println((i + 1) + ". " + deletable.get(i));
        }

        int choice = SafeScanner.getValidatedIntInput(sc, "Choose: ", 1, deletable.size());

        Enquiry enquiry = deletable.get(choice - 1);
        EnquiryController.deleteEnquiry(enquiry.getEnquiryId());

        System.out.println("Enquiry deleted successfully!");
    }
    private static void replyToHandledProjectEnquiries(Officer officer,Scanner sc) {

        List<Enquiry> replyable = EnquiryController.getEnquiriesForHandledProject(officer.getID());

        if (replyable.isEmpty()) {
            System.out.println("No pending enquiries to reply to.");
            return;
        }

        System.out.println("\n--- Replyable Enquiries ---");
        for (int i = 0; i < replyable.size(); i++) {
            System.out.println((i + 1) + ". " + replyable.get(i));
        }

        System.out.print("Select an enquiry to reply to (0 to cancel): ");
        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to menu.");
            return;
        }

        if (choice == 0) {
            System.out.println("Reply cancelled.");
            return;
        }

        if (choice < 1 || choice > replyable.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Enquiry selected = replyable.get(choice - 1);
        System.out.print("Enter your reply: ");
        String replyContent = sc.nextLine();

        ReplyController.addReply(selected.getEnquiryId(), officer.getNric(), replyContent);

        System.out.println("Reply submitted and enquiry status updated.");
    }


    //Applicant Application Status Functionality
    private static void viewApplicantApplications(Officer officer) {
        int choice;
        Scanner sc = new Scanner(System.in);
        do {
            int numSus = ProjectApplicationController.getNumSusApplications(officer.getID());
            System.out.println("\n=== Applications ===");
            System.out.println("1. View all Applications");
            System.out.println("2. View Successful Applications " + ((numSus == 0) ? "" : "(" + numSus + ")"));
            System.out.println("3. View Filtered applications");
            System.out.println("4. Update Filters");
            System.out.println("0. Back");

            choice = SafeScanner.getValidatedIntInput(sc, "Enter your choice: ", 0, 4);

            switch (choice) {
                case 1 -> ProjectApplicationController.displayAllHandledProjectApplications(officer.getID());
                case 2 -> {
                    List<ProjectApplication> list = ProjectApplicationController.getHandledApplicationsByStatus(officer.getID(), ApplicationStatus.SUCCESSFUL);
                    if (list.isEmpty()) {
                        System.out.println("No Successful applications found.");
                    } else {
                        list.forEach(System.out::println);
                    }
                }
                case 3 -> System.out.println("TBC");
                case 4 -> System.out.println("TBC");
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
            if (choice == 2 && numSus > 0) {
                List<String> validOptions = Arrays.asList("y", "n");
                String selection = SafeScanner.getValidatedStringInput(sc, "Book application?\nEnter: y/n\n", validOptions);
                if (selection.equals("y")) {
                    List<String> validIDs = ProjectApplicationController.getSusApplicationIDs(officer.getID());
                    String applicationID = SafeScanner.getValidatedStringInput(sc, "Enter application ID: ", validIDs);
                    updateApplicationStatus(applicationID);
                }
            }
        }
        while (choice != 0);
    }
    private static void updateApplicationStatus(String applicationID) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Scanner sc = new Scanner(System.in);
        ProjectApplication application = getProjectApplicationsRepository().getByID(applicationID);
        Applicant applicant = ApplicantController.getApplicantById(application.getApplicantID());
        if(applicant == null) {
            Officer officer = OfficerController.getApplicantById(application.getApplicantID());
            Utils.PrettyPrint.prettyPrint(officer);
        }
        else {
            Utils.PrettyPrint.prettyPrint(applicant);
        }

        List<String> validOptions = Arrays.asList("b","exit");
        String selection = SafeScanner.getValidatedStringInput(sc, "\nUpdated Status: (b : Booked) \n", validOptions);
        ApplicationStatus status = null;
        switch (selection.toLowerCase()) {
            case "b" -> status = ApplicationStatus.BOOKED;
            case "exit" -> System.out.println("exiting...");
            default -> System.out.println("Invalid choice. Please select a valid option.");
        }
        application.setBook_date(LocalDate.parse(LocalDate.now().format(dtf)));
        if (ProjectApplicationController.updateApplicationStatus(application, status)) {
                Project project = ProjectController.getProjectByID(application.getProjectID());
                int numOfUnits;
                if(project.getType1().equalsIgnoreCase(application.getRoomType())){
                    numOfUnits = project.getNoOfUnitsType1();
                    numOfUnits -= 1;
                    ProjectController.updateProjectNumOfRoomType1(project.getID(),numOfUnits);
                }
                else{
                    numOfUnits = project.getNoOfUnitsType2();
                    numOfUnits -= 1;
                    ProjectController.updateProjectNumOfRoomType2(project.getID(),numOfUnits);
                }
                System.out.println("Application Booked!\n");


                System.out.println("\n Generating Receipt.....\n");
                ReceiptController.generateReceipt(officer.getName(),application);
        } else {
            System.out.println("Update failed, try again later\n");
        }
    }


    //Receipt Generation
    private void generateBookingReceipt(Officer officer,Scanner sc) {
        List<ProjectApplication> list = ProjectApplicationController.getHandledApplicationsByStatus(officer.getID(), ApplicationStatus.BOOKED);
        System.out.println("\n=== Receipt Generation ===\n");
        if (list.isEmpty()) {
            System.out.println("No booked applications found.");
        }
        else{
            for (int i = 0; i < list.size(); i++) {
                System.out.println((i + 1) + ". " + list.get(i));
            }
        }
        System.out.print("Select an application to generate a receipt to (0 to cancel): ");
        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to menu.");
            return;
        }

        if (choice == 0) {
            System.out.println("Generation selection canceled");
            return;
        }

        if (choice < 1 || choice > list.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        ProjectApplication application = list.get(choice - 1);
        ReceiptController.generateReceipt(officer.getName(),application);
    }
    //pretty print
    public static void prettyPrintProjectApplications(ProjectApplication application) {
        if (application == null) {
            System.out.println("No Application available.");
            return;
        }
        Project project = ProjectController.getProjectByID(application.getProjectID());
        System.out.println("\n======== Project Application ========\n");
        if (project == null) {
            System.out.println("Project details are not available.");
        }
        System.out.println("Application ID: " + application.getID());
        assert project != null;
        System.out.println("Project Name: " + project.getProjectName());
        System.out.println("Project ID: " + application.getProjectID());
        System.out.println("Room Type Applied: "+application.getRoomType());
        System.out.println("Status: " + application.getStatus());
    }
    public static void prettyPrintOfficerProject(Officer officer, Project project, boolean displayAll) {
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd MMM yyyy");
        if (officer.getMaritalStatus() == MaritalStatus.SINGLE && officer.getAge() >= 35 && !displayAll) {
            System.out.println("\nProject ID: "+project.getID());
            System.out.println("Project name: " + project.getProjectName());
            System.out.println("Neighbourhood: " + project.getNeighbourhood());
            if(project.getType1().equalsIgnoreCase("2-Room")){
                System.out.println("Room Type 1: "+project.getType1());
                System.out.println("Number of units for Room Type 1: "+project.getNoOfUnitsType1());
                System.out.println("Selling price of Room Type 1: "+project.getSellPriceType1());
            }
            else {
                System.out.println("Room Type 2: "+project.getType2());
                System.out.println("Number of units for Room Type 2: "+project.getNoOfUnitsType2());
                System.out.println("Selling price of Room Type 2: "+project.getSellPriceType2());
            }
            System.out.println("Application Open Date: "+project.getAppDateOpen().format(fmt1));
            System.out.println("Application Close Date: "+project.getAppDateClose().format(fmt1));
            System.out.println("Manager-in-charge: "+ ManagerController.getNameById(project.getManagerID()));

            System.out.println("------------------------");
        }
        else{
            System.out.println("\nProject ID: "+project.getID());
            System.out.println("Project name: " + project.getProjectName());
            System.out.println("Neighbourhood: " + project.getNeighbourhood());
            System.out.println("Room Type 1: "+project.getType1());
            System.out.println("Number of units for Room Type 1: "+project.getNoOfUnitsType1());
            System.out.println("Selling price of Room Type 1: "+project.getSellPriceType1());
            System.out.println("Room Type 2: "+project.getType2());
            System.out.println("Number of units for Room Type 2: "+project.getNoOfUnitsType2());
            System.out.println("Selling price of Room Type 2: "+project.getSellPriceType2());
            System.out.println("Application Open Date: "+project.getAppDateOpen().format(fmt1));
            System.out.println("Application Close Date: "+project.getAppDateClose().format(fmt1));
            System.out.println("Manager-in-charge: "+ManagerController.getNameById(project.getManagerID()));

            System.out.println("------------------------");
        }
    }
    public static void printPrettyReply(Reply reply) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Reply ID: " + reply.getReplyId());
        System.out.println("Enquiry ID: " + reply.getEnquiryId());
        System.out.println("Date: " + reply.getDate().format(dateFormatter));
        System.out.println("Officer / Manager ID: "+reply.getOfficerOrManagerId());
        System.out.println("Reply: "+reply.getReplyContent());
        System.out.println("----------------------\n");
    }
    public static void prettyPrintProject(Project project) {
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd MMM yyyy");
        System.out.println("\nProject ID: "+project.getID());
        System.out.println("Project name: " + project.getProjectName());
        System.out.println("Neighbourhood: " + project.getNeighbourhood());
        System.out.println("Application Open Date: "+project.getAppDateOpen().format(fmt1));
        System.out.println("Application Close Date: "+project.getAppDateClose().format(fmt1));
        System.out.println("Manager-in-charge: "+ ManagerController.getNameById(project.getManagerID()));
        System.out.println("------------------------");
    }



}
