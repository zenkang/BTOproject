package Enumerations;

/**
 * Represents the status of an enquiry.
 * <p>
 * This enumeration defines whether an enquiry has been addressed or is still awaiting a response.
 */
public enum EnquiryStatus {
    /**
     * The enquiry has been received but not yet replied to.
     */
    PENDING,

    /**
     * A response has been provided for the enquiry.
     */
    REPLIED
}
