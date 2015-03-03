package de.uniko.sebschlicht.graphity.bootstrap.load;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;

import de.uniko.sebschlicht.socialnet.Subscription;
import de.uniko.sebschlicht.socialnet.requests.Request;
import de.uniko.sebschlicht.socialnet.requests.RequestFollow;
import de.uniko.sebschlicht.socialnet.requests.RequestPost;
import de.uniko.sebschlicht.socialnet.requests.RequestUnfollow;

/**
 * Load the state of the social network state from the bootstrap log file.
 * 
 * @author sebschlicht
 * 
 */
public class BootstrapLoader {

    protected BootstrapFileLoader _fileLoader;

    /**
     * subscriptions currently present
     */
    protected TreeSet<Subscription> _subscriptions;

    /**
     * maps user identifier to number of posts
     */
    protected Map<Long, Integer> _numPosts;

    /**
     * Loads the state of the social network state from the bootstrap log file.
     * 
     * @param fBootstrapLog
     *            bootstrap log file
     * @throws IOException
     */
    public BootstrapLoader(
            File fBootstrapLog) throws IOException {
        _fileLoader = new BootstrapFileLoader(fBootstrapLog.getAbsolutePath());
        _subscriptions = new TreeSet<Subscription>();
        _numPosts = new HashMap<Long, Integer>();
        Queue<Request> requests = _fileLoader.loadRequests();
        mergeRequests(requests);
    }

    public TreeSet<Subscription> getSubscriptions() {
        return _subscriptions;
    }

    public Map<Long, Integer> getNumPosts() {
        return _numPosts;
    }

    protected void mergeRequests(Queue<Request> requests) {
        System.out.println("merging " + requests.size() + " requests...");

        RequestFollow rfo;
        RequestPost rp;
        RequestUnfollow ru;

        // load subscriptions and post counts
        Subscription subscription;
        for (Request request : requests) {
            switch (request.getType()) {
                case FOLLOW:
                    rfo = (RequestFollow) request;
                    subscription =
                            new Subscription(rfo.getIdSubscriber(),
                                    rfo.getIdFollowed());
                    _subscriptions.add(subscription);
                    addPost(rfo.getIdSubscriber());
                    addPost(rfo.getIdFollowed());
                    break;

                case POST:
                    rp = (RequestPost) request;
                    addPost(rp.getId());
                    break;

                case UNFOLLOW:
                    ru = (RequestUnfollow) request;
                    subscription =
                            new Subscription(ru.getIdSubscriber(),
                                    ru.getIdFollowed());
                    _subscriptions.remove(subscription);
                    addPost(ru.getIdSubscriber());
                    addPost(ru.getIdFollowed());
                    break;

                default:
                    throw new IllegalStateException("unknown request type \""
                            + request.getType() + "\"");
            }
        }
    }

    private void addPost(long id) {
        Integer numUserPosts = _numPosts.get(id);
        if (numUserPosts != null) {
            //TODO does this work as expected?
            numUserPosts += 1;
        } else {
            _numPosts.put(id, 1);
        }
    }
}
