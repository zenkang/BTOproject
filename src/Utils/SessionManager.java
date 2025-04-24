package Utils;

/**
 * The SessionManager class provides global flags that track session-related states.
 * This includes flags such as whether a user has changed their password during the current session.
 */
public class SessionManager {

    /**
     * Indicates whether the user has changed their password during the session.
     * Used to enforce re-login or confirmation after a successful password change.
     */
    public static boolean passwordChanged = false;

}
