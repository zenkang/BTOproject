package Enquiry;

import Manager.Manager;
import Project.Project;
import Project.ProjectController;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class EnquiryManagerBoundary {
    private static final Scanner sc = new Scanner(System.in);

    public static void managerMenu(Manager manager) {
        while (true) {
            System.out.println("\n--- Enquiry Menu (Manager) ---");
            System.out.println("1. View All Enquiries");
            System.out.println("2. Reply to Enquiries for Your Projects");
            System.out.println("0. Back");
            System.out.print("Enter option: ");
            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> viewAllEnquiries();
                case 2 -> replyToOwnProjectEnquiries(manager);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void viewAllEnquiries() {
        List<Enquiry> enquiries = EnquiryController.getAllEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries available.");
            return;
        }

        System.out.println("\n--- All Enquiries ---");
        for (Enquiry e : enquiries) {
            System.out.println(e);
        }
    }

    private static void replyToOwnProjectEnquiries(Manager manager) {
        List<Project> myProjects = ProjectController.getProjectsManagedBy(manager.getName());
        List<String> myProjectNames = myProjects.stream()
                .map(Project::getProjectName)
                .collect(Collectors.toList());

        List<Enquiry> replyable = EnquiryController.getAllEnquiries().stream()
                .filter(e ->
                        myProjectNames.stream().anyMatch(
                                p -> p.trim().equalsIgnoreCase(e.getProjectName().trim())
                        )
                                && !e.isReplied()
                )
                .collect(Collectors.toList());

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

        ReplyController.addReply(selected.getEnquiryId(), manager.getNric(), replyContent);

        System.out.println("Reply submitted and enquiry status updated.");
    }
}
