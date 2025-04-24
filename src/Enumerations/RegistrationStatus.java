package Enumerations;

/**
 * Represents the status of a registration process.
 * <p>
 * This enumeration defines the different stages or outcomes of a registration.
 */
public enum RegistrationStatus {

    /**
     * The registration has been submitted and is awaiting review or approval.
     */
    PENDING,

    /**
     * The registration has been reviewed and approved.
     */
    APPROVED,

    /**
     * The registration has been reviewed and rejected.
     */
    REJECTED
}
