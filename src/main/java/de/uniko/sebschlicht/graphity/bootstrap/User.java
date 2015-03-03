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

    //TODO in Neo4j these are only valid as long as no node was deleted!
    private long _nodeId;

    //TODO use long array if too memory consumptive
    private long[] _subscriptions;

    private long[] _postNodeIds;

    public User() {
        _subscriptions = null;
        _postNodeIds = null;
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

    public void setPostNodeIds(long[] postNodeIds) {
        _postNodeIds = postNodeIds;
    }

    public long[] getPostNodeIds() {
        return _postNodeIds;
    }
}
