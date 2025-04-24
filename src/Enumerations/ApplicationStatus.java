package Enumerations;

/**
 * Represents the possible statuses of an application.
 * <p>
 * This enumeration defines the various outcomes or stages an application can be in.
 */
public enum ApplicationStatus {
    /**
     * The application has been submitted and is awaiting review.
     */
    PENDING,

    /**
     * The application has been reviewed and approved successfully.
     */
    SUCCESSFUL,

    /**
     * The application was reviewed but not approved.
     */
    UNSUCCESSFUL,

    /**
     * The applicant has secured a booking based on a successful application.
     */
    BOOKED
}
