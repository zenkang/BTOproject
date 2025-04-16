package Enquiry;

import Applicant.Applicant;
import Utils.SafeScanner;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class EnquiryApplicantBoundary {
    private static final Scanner sc = new Scanner(System.in);

    public static void applicantMenu(Applicant applicant) {
        System.out.println("\n--- Enquiry Menu (Applicant) ---");
        System.out.println("1. Submit new enquiry");
        System.out.println("2. View my enquiries");
        System.out.println("3. Edit an existing enquiry");
        System.out.println("4. Delete an enquiry");

        int choice = SafeScanner.getValidatedIntInput(sc, "Enter option: ", 1, 4);

        switch (choice) {
            case 1 -> submitEnquiry(applicant.getNric());
            case 2 -> viewEnquiries(applicant.getNric());
            case 3 -> editEnquiry(applicant.getNric());
            case 4 -> deleteEnquiry(applicant.getNric());
            default -> System.out.println("Invalid option.");
        }
    }

    public static void submitEnquiry(String nric) {
        String projectName = SafeScanner.getValidProjectName(sc);
        System.out.print("Enter your message: ");
        String message = sc.nextLine();

        String id = UUID.randomUUID().toString().substring(0, 8);
        LocalDate today = LocalDate.now();

        Enquiry enquiry = new Enquiry(id, today, projectName, nric, message);
        EnquiryController.addEnquiry(enquiry);

        System.out.println("Enquiry submitted!");
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
}
