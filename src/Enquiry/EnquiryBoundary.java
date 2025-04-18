package Enquiry;

import java.util.Scanner;
import java.util.UUID;

import Project.Project;
import Project.ProjectController;
import Utils.SafeScanner;

import java.util.ArrayList;

public class EnquiryBoundary {
	private static Scanner sc = new Scanner(System.in);

    public static void applicantMenu(String nric) {
        System.out.println("1. Submit new enquiry");
        System.out.println("2. View my enquiries");
        System.out.println("3. Edit an existing enquiry");
        System.out.println("4. Delete an enquiry");
        int choice = sc.nextInt();
        sc.nextLine(); // clear newline

        switch (choice) {
            case 1 -> submitEnquiry(nric);
            case 2 -> viewEnquiries(nric);
            case 3 -> editEnquiry(nric);
            case 4 -> deleteEnquiry(nric);
            default -> System.out.println("Invalid option.");
        }
    }

    public static void submitEnquiry(String nric) {
    	String projectName = SafeScanner.getValidProjectName(sc);
    	
        System.out.print("Enter your message: ");
        String message = sc.nextLine();

        String id = UUID.randomUUID().toString().substring(0, 8); // Short unique ID
        Enquiry enquiry = new Enquiry(id, nric, projectName, message);
        EnquiryController.addEnquiry(enquiry);

        System.out.println("Enquiry submitted!");
    }

    public static void editEnquiry(String nric) {
        ArrayList<Enquiry> enquiries = EnquiryController.getEnquiriesByApplicant(nric);
        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        System.out.println("Select an enquiry to edit:");
        for (int i = 0; i < enquiries.size(); i++) {
            System.out.println((i + 1) + ". " + enquiries.get(i));
        }

        int choice = sc.nextInt();
        sc.nextLine(); // clear newline

        if (choice < 1 || choice > enquiries.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Enquiry enquiry = enquiries.get(choice - 1);
        System.out.print("Enter new message for enquiry: ");
        String newMessage = sc.nextLine();
        enquiry.setMessage(newMessage); 

        System.out.println("Enquiry updated successfully!");
    }

    public static void deleteEnquiry(String nric) {
        ArrayList<Enquiry> enquiries = EnquiryController.getEnquiriesByApplicant(nric);
        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries to delete.");
            return;
        }

        System.out.println("Select an enquiry to delete:");
        for (int i = 0; i < enquiries.size(); i++) {
            System.out.println((i + 1) + ". " + enquiries.get(i));
        }

        int choice = sc.nextInt();
        sc.nextLine(); // clear newline

        if (choice < 1 || choice > enquiries.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        Enquiry enquiry = enquiries.get(choice - 1);
        EnquiryController.deleteEnquiry(enquiry.getEnquiryId());

        System.out.println("Enquiry deleted successfully!");
    }

    
    public static void viewEnquiries(String nric) {
        ArrayList<Enquiry> enquiries = EnquiryController.getEnquiriesByApplicant(nric);
        if (enquiries.isEmpty()) {
            System.out.println("You have no enquiries.");
        } else {
            for (Enquiry e : enquiries) {
                System.out.println(e);
            }
        }
    }


}
