package Applicant;

import java.util.Scanner;
import Utils.SafeScanner;

public class ApplicantBoundary {
    private Applicant applicant;

    public ApplicantBoundary(Applicant applicant) {
        this.applicant = applicant;
    }

    public void displayMenu() {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\n=== Doctor Menu ===");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("0. Exit");

            choice = SafeScanner.getValidatedIntInput(scanner, "Enter your choice: ", 0, 7);

            switch (choice) {
                case 1 -> //viewPatientMedicalRecords();
                case 2 -> //updatePatientMedicalRecords();
                case 3 -> //viewPersonalSchedule();
                case 4 -> //setAppointmentAvailability();
                case 5 -> //handleAppointmentRequests();
                case 6 -> //viewUpcomingAppointments();
                case 7 -> //recordAppointmentOutcome();
                case 0 -> System.out.println("Exiting the Doctor Menu.");
                default -> System.out.println("Invalid choice. Please select a valid option.");
            }
        } while (choice != 0);


    }
}
