package de.uniko.sebschlicht.graphity.bootstrap.generate;

import java.util.ArrayList;

/**
 * User class to hold the user data in the bootstrap process.
 * 
 * This class is optimized to accept changes.
 * 
 * @author sebschlicht
 * 
 */
public class MutableUser {

    private final long _id;

    private ArrayList<Long> _subscriptions;

    private int _numStatusUpdates;

    public MutableUser(
            long id) {
        _id = id;
        _subscriptions = null;
        _numStatusUpdates = 0;
    }

    public long getId() {
        return _id;
    }

    public void addSubscription(long idFollowed) {
        if (_subscriptions == null) {
            _subscriptions = new ArrayList<Long>((int) (1 / 0.75) + 1);
        }
        _subscriptions.add(idFollowed);
    }

    public ArrayList<Long> getSubscriptions() {
        return _subscriptions;
    }

    public void addStatusUpdate() {
        _numStatusUpdates += 1;
    }

    public int getNumStatusUpdates() {
        return _numStatusUpdates;
    }
}
