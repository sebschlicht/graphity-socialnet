package de.uniko.sebschlicht.graphity.bootstrap;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

import de.uniko.sebschlicht.graphity.bootstrap.generate.MutableState;
import de.uniko.sebschlicht.graphity.bootstrap.load.BootstrapLoader;
import de.uniko.sebschlicht.socialnet.Subscription;

public abstract class BootstrapClient {

    protected static boolean IS_GRAPHITY = false;

    protected static Random RANDOM = new Random();

    /**
     * allowed characters for posts
     */
    protected static final char[] POST_SYMBOLS;
    static {
        StringBuilder postSymbols = new StringBuilder();
        // numbers
        for (char number = '0'; number <= '9'; ++number) {
            postSymbols.append(number);
        }
        // lower case letters
        for (char letter = 'a'; letter <= 'z'; ++letter) {
            postSymbols.append(letter);
        }
        // upper case letters
        for (char letter = 'a'; letter <= 'z'; ++letter) {
            postSymbols.append(Character.toUpperCase(letter));
        }
        POST_SYMBOLS = postSymbols.toString().toCharArray();
    }

    protected UserManager _users;

    /**
     * Bulk load a social network state into the news feed service storage.
     * The social network state is read from the bootstrap log file.
     * This method has to load all the data in a single call.
     * 
     * @param fBootstrapLog
     *            bootstrap log file
     * @throws IOException
     */
    public void bootstrap(File fBootstrapLog) throws IOException {
        BootstrapLoader bootstrapLoader =
                new BootstrapLoader(fBootstrapLog, true);
        MutableState state = bootstrapLoader.getState();
        _users = new UserManager();

        // convert from mutable to final social network state
        long prevUserId = -1, userId = 0;
        User user;
        List<Long> userSubscriptions = null;

        // load subscriptions and followers
        TreeSet<Subscription> subscriptions = state.getSubscriptions();
        int iSubscription = 0;
        int numSubscriptions = subscriptions.size();
        for (Subscription subscription : subscriptions) {
            // switch to current user
            userId = subscription.getIdSubscriber();
            if (userId != prevUserId) {
                // make previous user persistent when switching to a new user
                if (userSubscriptions != null) {
                    user = _users.addUser(prevUserId);
                    long[] aUserSubscriptions = listToArray(userSubscriptions);
                    user.setSubscriptions(aUserSubscriptions);
                }
                // switch to new user
                userSubscriptions = new LinkedList<Long>();
                prevUserId = userId;
            }

            // add subscription for current user
            userSubscriptions.add(subscription.getIdFollowed());

            // make persistent if last user
            if (iSubscription == numSubscriptions - 1) {
                user = _users.addUser(userId);
                long[] aUserSubscriptions = listToArray(userSubscriptions);
                user.setSubscriptions(aUserSubscriptions);
            }
            iSubscription += 1;
        }

        // load posts and authors
        Map<Long, int[]> numPosts = state.getNumPosts();
        for (Map.Entry<Long, int[]> entry : numPosts.entrySet()) {
            user = _users.loadUser(entry.getKey());
            int[] numUserPosts = entry.getValue();
            long[] postNodeIds = new long[numUserPosts[1]];// total number of posts // we are bootstrapping!
            user.setPostNodeIds(postNodeIds);
        }

        bootstrap();
    }

    protected void bootstrap() {
        long numUsers = createUsers();
        System.out.println(numUsers + " users created.");

        // subscribe users
        long numSubscriptions = createSubscriptions();
        System.out.println(numSubscriptions + " subscriptions registered.");

        // create and link posts
        long numPostsTotal = createPosts();
        System.out.println(numPostsTotal + " posts created.");
        linkPosts();
        System.out.println("posts linked.");
    }

    /**
     * Add the users to the database.
     * 
     * @return number of users
     */
    abstract protected long createUsers();

    /**
     * Link the users in the database.
     * 
     * @return number of subscriptions
     */
    abstract protected long createSubscriptions();

    /**
     * Add the posts to the database.
     * 
     * @return number of posts
     */
    abstract protected long createPosts();

    /**
     * Link the posts in the database.
     */
    abstract protected void linkPosts();

    /**
     * Generates a post message using random characters of the allowed
     * characters.
     * 
     * @param length
     *            post message length
     * @return random post message with the length specified
     */
    protected static String generatePostMessage(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            builder.append(getRandomPostChar());
        }
        return builder.toString();
    }

    /**
     * @return random character allowed in a post
     */
    protected static char getRandomPostChar() {
        return POST_SYMBOLS[RANDOM.nextInt(POST_SYMBOLS.length)];
    }

    /**
     * Converts a list with user identifiers to an array.
     * 
     * @param list
     *            user identifier list
     * @return array representation of the list
     */
    private static long[] listToArray(List<Long> list) {
        long[] array = new long[list.size()];
        int i = 0;
        for (long value : list) {
            array[i] = value;
            i += 1;
        }
        return array;
    }
}
