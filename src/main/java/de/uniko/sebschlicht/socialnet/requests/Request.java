package de.uniko.sebschlicht.socialnet.requests;

import java.util.LinkedList;
import java.util.List;

public abstract class Request {

    protected RequestType _type;

    protected String _address;

    protected Throwable _throwable;

    public Request(
            RequestType type) {
        _type = type;
    }

    public RequestType getType() {
        return _type;
    }

    public void setAddress(String address) {
        _address = address;
    }

    public String getAddress() {
        return _address;
    }

    public void setError(Throwable throwable) {
        _throwable = throwable;
    }

    public boolean hasFailed() {
        return (_throwable != null);
    }

    public Throwable getError() {
        return _throwable;
    }

    abstract public String[] toStringArray();

    public static List<Request> parseFromStringArray(String[] saRequests) {
        List<Request> requests = new LinkedList<Request>();
        int type;
        long id1, id2;
        for (int i = 0; i < saRequests.length;) {
            type = Integer.valueOf(saRequests[i++]);
            if (type == RequestType.FEED.getId()) {
                id1 = Long.valueOf(saRequests[i++]);
                requests.add(new RequestFeed(id1));
            } else if (type == RequestType.FOLLOW.getId()) {
                id1 = Long.valueOf(saRequests[i++]);
                id2 = Long.valueOf(saRequests[i++]);
                requests.add(new RequestFollow(id1, id2));
            } else if (type == RequestType.POST.getId()) {
                id1 = Long.valueOf(saRequests[i++]);
                requests.add(new RequestPost(id1, saRequests[i++]));
            } else {// UNFOLLOW
                id1 = Long.valueOf(saRequests[i++]);
                id2 = Long.valueOf(saRequests[i++]);
                requests.add(new RequestUnfollow(id1, id2));
            }
        }
        return requests;
    }
}
