package de.uniko.sebschlicht.graphity.exception;

/**
 * An unknown user identifier was passed as reading identifier.
 * 
 * @author sebschlicht
 * 
 */
public class UnknownReaderIdException extends Exception {

    private static final long serialVersionUID = -4110476478476975262L;

    /**
     * reading user identifier passed
     */
    private final String idReader;

    /**
     * Creates an exception to express that the identifier passed for the
     * reading user is unknown.
     * 
     * @param idReader
     *            reading user identifier passed
     */
    public UnknownReaderIdException(
            String idReader) {
        this.idReader = idReader;
    }

    /**
     * @return followed user identifier passed
     */
    public String getFollowedId() {
        return idReader;
    }

    @Override
    public String toString() {
        return "there is no user with identifier \"" + idReader
                + "\" passed as reading user";
    }
}
