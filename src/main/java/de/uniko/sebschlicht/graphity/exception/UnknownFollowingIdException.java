package de.uniko.sebschlicht.graphity.exception;

/**
 * An unknown user identifier was passed as following identifier.
 * 
 * @author sebschlicht
 * 
 */
public class UnknownFollowingIdException extends Exception {

    private static final long serialVersionUID = -1228234431314197210L;

    /**
     * following user identifier passed
     */
    private final String idFollowing;

    /**
     * Creates an exception to express that the identifier passed for the
     * following user is unknown.
     * 
     * @param idFollowing
     *            following user identifier passed
     */
    public UnknownFollowingIdException(
            String idFollowing) {
        this.idFollowing = idFollowing;
    }

    /**
     * @return following user identifier passed
     */
    public String getFollowingId() {
        return idFollowing;
    }

    @Override
    public String getMessage() {
        return "there is no user with identifier \"" + idFollowing
                + "\" passed as following user";
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
