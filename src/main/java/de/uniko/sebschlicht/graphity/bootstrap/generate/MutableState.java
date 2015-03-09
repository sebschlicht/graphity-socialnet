package de.uniko.sebschlicht.graphity.bootstrap.generate;

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
 * Holds the social network state.
 * 
 * This class is optimized to apply changes to the state.
 * 
 * @author sebschlicht
 * 
 */
public class MutableState {

    /**
     * requests leading to the current social network state
     */
    protected Queue<Request> _requests;

    /**
     * subscriptions currently present
     */
    protected TreeSet<Subscription> _subscriptions;

    /**
     * maps user identifier to number of posts ([0] user-generated, [1] total)
     */
    protected Map<Long, int[]> _numPosts;

    public MutableState() {
        _subscriptions = new TreeSet<Subscription>();
        _numPosts = new HashMap<>();
    }

    public boolean addSubscription(Subscription subscription) {
        return _subscriptions.add(subscription);
    }

    public boolean removeSubscription(Subscription subscription) {
        return _subscriptions.remove(subscription);
    }

    public TreeSet<Subscription> getSubscriptions() {
        return _subscriptions;
    }

    public void addPost(long userId, boolean generated) {
        int[] numUserPosts = _numPosts.get(userId);
        if (numUserPosts == null) {
            numUserPosts = new int[] {
                0, 0
            };
            _numPosts.put(userId, numUserPosts);
        }
        if (!generated) {
            numUserPosts[0] += 1;
        }
        numUserPosts[1] += 1;
    }

    public Map<Long, int[]> getNumPosts() {
        return _numPosts;
    }

    public void setRequests(Queue<Request> requests, boolean pushPosts) {
        _requests = requests;
        mergeRequests(requests, pushPosts);
    }

    public void addRequests(Queue<Request> requests) {
        _requests.addAll(requests);
    }

    public Queue<Request> getRequests() {
        return _requests;
    }

    /**
     * Applies requests to the current social network state.
     * 
     * @param requests
     *            requests to be merged
     * @param pushPosts
     *            if set to true, updates of subscriptions will generate post
     *            messages for the respective users
     * @throws IllegalStateException
     *             if an unknown request type occurs
     */
    public void mergeRequests(Queue<Request> requests, boolean pushPosts) {
        for (Request request : requests) {
            mergeRequest(request, pushPosts);
        }
    }

    /**
     * Applies a request to the current social network state.
     * 
     * @param request
     *            request to be merged
     * @param pushPosts
     *            if set to true, updates of subscriptions will generate post
     *            messages for the respective users
     * @throws IllegalStateException
     *             if an unknown request type occurs
     */
    public void mergeRequest(Request request, boolean pushPosts) {
        Subscription subscription;
        switch (request.getType()) {
            case FOLLOW:
                RequestFollow rfo = (RequestFollow) request;
                subscription =
                        new Subscription(rfo.getIdSubscriber(),
                                rfo.getIdFollowed());
                addSubscription(subscription);
                if (pushPosts) {
                    addPost(rfo.getIdSubscriber(), true);
                    addPost(rfo.getIdFollowed(), true);
                }
                break;

            case POST:
                RequestPost rp = (RequestPost) request;
                addPost(rp.getId(), false);
                break;

            case UNFOLLOW:
                RequestUnfollow ru = (RequestUnfollow) request;
                subscription =
                        new Subscription(ru.getIdSubscriber(),
                                ru.getIdFollowed());
                removeSubscription(subscription);
                if (pushPosts) {
                    addPost(ru.getIdSubscriber(), true);
                    addPost(ru.getIdFollowed(), true);
                }
                break;

            default:
                throw new IllegalStateException("unknown request type \""
                        + request.getType() + "\"");
        }
    }
}
