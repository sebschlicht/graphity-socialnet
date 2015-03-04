package de.uniko.sebschlicht.graphity.bootstrap.load;

import java.io.File;
import java.io.IOException;
import java.util.Queue;

import de.uniko.sebschlicht.graphity.bootstrap.generate.MutableState;
import de.uniko.sebschlicht.socialnet.requests.Request;

/**
 * Load the state of social networks from bootstrap log files.
 * 
 * @author sebschlicht
 * 
 */
public class BootstrapLoader {

    /**
     * bootstrap log file loader
     */
    protected BootstrapFileLoader _fileLoader;

    /**
     * mutable social network state
     */
    protected MutableState _state;

    /**
     * Loads the state of a social network from a bootstrap log file.
     * 
     * @param fBootstrapLog
     *            bootstrap log file
     * @param pushPosts
     *            see MutableState.mergeRequests:pushPosts
     * @throws IOException
     */
    public BootstrapLoader(
            File fBootstrapLog,
            boolean pushPosts) throws IOException {
        _fileLoader = new BootstrapFileLoader(fBootstrapLog.getAbsolutePath());
        _state = new MutableState();
        Queue<Request> requests = _fileLoader.loadRequests();
        System.out.println("merging " + requests.size() + " requests...");
        _state.mergeRequests(requests, pushPosts);
    }

    public MutableState getState() {
        return _state;
    }
}
