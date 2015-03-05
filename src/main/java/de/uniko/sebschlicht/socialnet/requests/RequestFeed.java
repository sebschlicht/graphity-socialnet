package de.uniko.sebschlicht.socialnet.requests;

public class RequestFeed extends Request {

    private long id;

    private int resNumFeeds;

    public RequestFeed(
            long id) {
        super(RequestType.FEED);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setResult(int numFeeds) {
        resNumFeeds = numFeeds;
    }

    public int getResult() {
        return resNumFeeds;
    }

    @Override
    public String[] toStringArray() {
        throw new IllegalStateException(
                "toStringArray not implemented for RequestFeed!");
    }
}
