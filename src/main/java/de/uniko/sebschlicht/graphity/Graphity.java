package de.uniko.sebschlicht.graphity;

import de.uniko.sebschlicht.graphity.exception.IllegalUserIdException;
import de.uniko.sebschlicht.graphity.exception.UnknownFollowedIdException;
import de.uniko.sebschlicht.graphity.exception.UnknownFollowingIdException;
import de.uniko.sebschlicht.graphity.exception.UnknownReaderIdException;
import de.uniko.sebschlicht.socialnet.StatusUpdateList;

public abstract class Graphity {

    protected static final String INDEX_USER_ID_NAME = "user.id";

    public abstract void init();

    public abstract boolean addUser(String userIdentifier)
            throws IllegalUserIdException;

    public abstract boolean
        addFollowship(String idFollowing, String idFollowed)
                throws IllegalUserIdException;

    public abstract boolean removeFollowship(
            String idFollowing,
            String idFollowed) throws IllegalUserIdException,
            UnknownFollowingIdException, UnknownFollowedIdException;

    public abstract long addStatusUpdate(String idAuthor, String message)
            throws IllegalUserIdException;

    public abstract StatusUpdateList readStatusUpdates(
            String idReader,
            int numStatusUpdates) throws IllegalUserIdException,
            UnknownReaderIdException;
}
