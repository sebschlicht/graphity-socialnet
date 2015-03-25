package de.uniko.sebschlicht.socialnet.requests;

public enum RequestType {

    FEED(0),

    FOLLOW(1),

    POST(2),

    UNFOLLOW(3),

    USER(4);

    private final int id;

    private RequestType(
            int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static RequestType getTypeById(int id) {
        if (FEED.getId() == id) {
            return FEED;
        } else if (FOLLOW.getId() == id) {
            return FOLLOW;
        } else if (POST.getId() == id) {
            return POST;
        } else if (UNFOLLOW.getId() == id) {
            return UNFOLLOW;
        } else if (USER.getId() == id) {
            return USER;
        }
        return null;
    }
}
