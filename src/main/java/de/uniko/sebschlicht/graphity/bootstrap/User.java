package de.uniko.sebschlicht.graphity.bootstrap;

/**
 * User class to hold the user data in the bootstrap process.
 * 
 * This class is optimized to store and read the current state, not to generate
 * a new state.
 * 
 * @author sebschlicht
 * 
 */
public class User {

    private final long _id;

    //TODO in Neo4j these are only valid as long as no node was deleted!
    private long _nodeId;

    //TODO use long array if too memory consumptive
    private long[] _subscriptions;

    /**
     * total number of posts
     */
    private int _numPosts;

    /**
     * identifiers of user-generated post nodes
     */
    private long[] _postNodeIds;

    public User(
            long id) {
        _id = id;
        _subscriptions = null;
        _postNodeIds = null;
    }

    public long getId() {
        return _id;
    }

    public void setNodeId(long nodeId) {
        _nodeId = nodeId;
    }

    public long getNodeId() {
        return _nodeId;
    }

    public void setSubscriptions(long[] subscriptions) {
        _subscriptions = subscriptions;
    }

    public long[] getSubscriptions() {
        return _subscriptions;
    }

    public void setNumPosts(int numPosts) {
        _numPosts = numPosts;
    }

    public int getNumPosts() {
        return _numPosts;
    }

    public void setPostNodeIds(long[] postNodeIds) {
        _postNodeIds = postNodeIds;
    }

    public long[] getPostNodeIds() {
        return _postNodeIds;
    }
}
