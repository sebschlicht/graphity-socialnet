package de.uniko.sebschlicht.socialnet.requests;

public class RequestFollow extends Request {

    private long idSubscriber;

    private long idFollowed;

    private boolean resSuccess;

    public RequestFollow(
            long idSubscriber,
            long idFollowed) {
        super(RequestType.FOLLOW);
        this.idSubscriber = idSubscriber;
        this.idFollowed = idFollowed;
    }

    public long getIdSubscriber() {
        return idSubscriber;
    }

    public long getIdFollowed() {
        return idFollowed;
    }

    public void setResult(boolean success) {
        resSuccess = success;
    }

    public boolean getResult() {
        return resSuccess;
    }

    @Override
    public String[] toStringArray() {
        return new String[] {
            String.valueOf(idSubscriber), String.valueOf(idFollowed)
        };
    }
}
