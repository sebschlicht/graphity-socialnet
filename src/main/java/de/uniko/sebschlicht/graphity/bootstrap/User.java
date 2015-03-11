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
public class User implements Comparable<User> {

    private final long _id;

    //TODO in Neo4j these are only valid as long as no node was deleted!
    private long _nodeId;

    private long[] _subscriptions;

    /**
     * identifiers of the user replicas<br>
     * order equals the order of subscriptions
     */
    private long[] _replicas;

    /**
     * total number of posts
     */
    private int _numPosts;

    /**
     * timestamp of last recent news item
     */
    private long _tsLastPost;

    /**
     * identifiers of user-generated post nodes
     */
    private long[] _postNodeIds;

    public User(
            long id) {
        _id = id;
        _subscriptions = null;
        _replicas = null;
        _postNodeIds = null;
        _tsLastPost = -1;
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

    public void setReplicas(long[] replicas) {
        _replicas = replicas;
    }

    public long[] getReplicas() {
        return _replicas;
    }

    public void setNumPosts(int numPosts) {
        _numPosts = numPosts;
    }

    public int getNumPosts() {
        return _numPosts;
    }

    public void setTsLastPost(long tsLastPost) {
        _tsLastPost = tsLastPost;
    }

    public long getTsLastPost() {
        return _tsLastPost;
    }

    public void setPostNodeIds(long[] postNodeIds) {
        _postNodeIds = postNodeIds;
    }

    public long[] getPostNodeIds() {
        return _postNodeIds;
    }

    @Override
    public int compareTo(final User u) {
        if (u == this) {
            return 0;
        }
        if (_tsLastPost > u.getTsLastPost()) {// this user has more recent news items
            return 1;
        } else if (_tsLastPost < u.getTsLastPost()) {// this user has less recent news item
            return -1;
        } else {// the users have equally recent news items; does not have to be consistent
            return 0;
        }
    }
}
