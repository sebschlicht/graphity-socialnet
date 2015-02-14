package de.uniko.sebschlicht.graphity.exception;

/**
 * An unknown user identifier was passed as followed identifier.
 * 
 * @author sebschlicht
 * 
 */
public class UnknownFollowedIdException extends Exception {

    private static final long serialVersionUID = 7510416936994013428L;

    /**
     * followed user identifier passed
     */
    private final String idFollowed;

    /**
     * Creates an exception to express that the identifier passed for the
     * followed user is unknown.
     * 
     * @param idFollowed
     *            followed user identifier passed
     */
    public UnknownFollowedIdException(
            String idFollowed) {
        this.idFollowed = idFollowed;
    }

    /**
     * @return followed user identifier passed
     */
    public String getFollowedId() {
        return idFollowed;
    }

    @Override
    public String getMessage() {
        return "there is no user with identifier \"" + idFollowed
                + "\" passed as followed user";
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
