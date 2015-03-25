package de.uniko.sebschlicht.graphity.bootstrap.load;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import de.uniko.sebschlicht.socialnet.requests.Request;
import de.uniko.sebschlicht.socialnet.requests.RequestFeed;
import de.uniko.sebschlicht.socialnet.requests.RequestFollow;
import de.uniko.sebschlicht.socialnet.requests.RequestPost;
import de.uniko.sebschlicht.socialnet.requests.RequestType;
import de.uniko.sebschlicht.socialnet.requests.RequestUnfollow;
import de.uniko.sebschlicht.socialnet.requests.RequestUser;

public class BootstrapFileLoader extends CsvParser<Request> {

    public BootstrapFileLoader(
            String filePath) throws FileNotFoundException {
        super(filePath);
    }

    public Queue<Request> loadRequests() throws IOException {
        Queue<Request> requests = new LinkedList<>();
        Request request;
        while ((request = getRequest()) != null) {
            requests.add(request);
        }
        return requests;
    }

    public Request getRequest() throws IOException {
        String[] entry = getEntry();
        if (entry == null) {
            return null;
        }
        if (entry.length == 0) {// skip empty line
            return getRequest();
        }
        int typeId = Integer.valueOf(entry[0]);
        RequestType type = RequestType.getTypeById(typeId);
        if (type == null) {
            throw new IllegalStateException(
                    "unknown request type identifier \"" + typeId + "\"");
        }
        long idActor = Long.valueOf(entry[1]);
        long idFollowed;
        switch (type) {
            case FEED:
                return new RequestFeed(idActor);
            case FOLLOW:
                idFollowed = Long.valueOf(entry[2]);
                return new RequestFollow(idActor, idFollowed);
            case POST:
                String message = null;
                if (entry.length > 2) {// post message already generated
                    message = entry[2];
                }
                return new RequestPost(idActor, message);
            case UNFOLLOW:
                idFollowed = Long.valueOf(entry[2]);
                return new RequestUnfollow(idActor, idFollowed);
            case USER:
                return new RequestUser(idActor);
            default:
                throw new IllegalStateException("unknown request type \""
                        + type + "\"");
        }
    }
}
