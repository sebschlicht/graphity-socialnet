package de.uniko.sebschlicht.graphity.exception;

/**
 * user identifier is invalid for one of the following reasons
 * <ul>
 * <li>not a number</li>
 * <li>value is zero or below</li>
 * </ul>
 * 
 * @author sebschlicht
 * 
 */
public class IllegalUserIdException extends Exception {

    private static final long serialVersionUID = 598658423383590791L;

    public IllegalUserIdException(
            String userIdentifier) {
        super("Illegal user identifier \"" + userIdentifier
                + "\": must be a number with value greater than zero");
    }
}
