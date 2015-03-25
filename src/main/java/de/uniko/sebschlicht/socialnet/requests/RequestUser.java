package de.uniko.sebschlicht.socialnet.requests;

public class RequestUser extends Request {

    private long _id;

    private boolean _hasSucceeded;

    public RequestUser(
            long id) {
        super(RequestType.USER);
        _id = id;
    }

    public long getId() {
        return _id;
    }

    public void setResult(boolean success) {
        _hasSucceeded = success;
    }

    public boolean getResult() {
        return _hasSucceeded;
    }

    @Override
    public String[] toStringArray() {
        return new String[] {
            String.valueOf(_type.getId()), String.valueOf(_id)
        };
    }
}
