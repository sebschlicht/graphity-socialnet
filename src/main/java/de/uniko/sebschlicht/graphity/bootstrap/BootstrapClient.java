package de.uniko.sebschlicht.graphity.bootstrap;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

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
     * This method has to load all the data in a single call.
     * 
     * @param userIds
     *            user identifiers
     * @param subscriptions
     *            list of arrays with identifier of users the respective user
     *            subscribed to, list has the same order as userIds
     * @param numPosts
     *            number of posts per respective user, array has the same order
     *            as userIds
     */
    public void bootstrap(
            long[] userIds,
            List<long[]> subscriptions,
            int[] numPosts) {
        _users = new UserManager();
        // load users
        _users.setUserIds(userIds);
        for (int i = 0; i < userIds.length; ++i) {
            _users.addUserByIndex(i);
        }
        System.out.println("users loaded.");
        createUsers(userIds);
        System.out.println(userIds.length + " users created.");

        // subscribe users
        long numSubscriptions = createSubscriptions(subscriptions);
        System.out.println(numSubscriptions + " subscriptions registered.");

        // load, create and link posts
        long numPostsTotal = loadPosts(numPosts);
        System.out.println(numPostsTotal + " posts loaded.");
        createPosts(numPosts);
        System.out.println("posts created.");
        linkPosts(numPosts);
        System.out.println("posts linked.");
    }

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
        BootstrapLoader bootstrapLoader = new BootstrapLoader(fBootstrapLog);

        // convert from mutable to final social network state
        long prevUserId = -1, userId = 0;
        User user;
        List<Long> userSubscriptions = null;

        // load subscriptions and followers
        TreeSet<Subscription> subscriptions =
                bootstrapLoader.getSubscriptions();
        int iSubscription = 0;
        int numSubscriptions = subscriptions.size();
        for (Subscription subscription : subscriptions) {
            // switch to current user
            userId = subscription.getIdSubscriber();
            if (userId != prevUserId) {
                // make previous user persistent when switching to a new user
                if (userSubscriptions != null) {
                    user = _users.addUser(prevUserId);
                    long[] aUserSubscriptions =
                            new long[userSubscriptions.size()];
                    int i = 0;
                    for (long idFollowed : userSubscriptions) {
                        aUserSubscriptions[i] = idFollowed;
                        i += 1;
                    }
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
                long[] aUserSubscriptions = new long[userSubscriptions.size()];
                int i = 0;
                for (long idFollowed : userSubscriptions) {
                    aUserSubscriptions[i] = idFollowed;
                    i += 1;
                }
                user.setSubscriptions(aUserSubscriptions);
            }
            iSubscription += 1;
        }

        //TODO convert to proper state for bootstrapping
    }

    /**
     * Add the users to the database.
     * 
     * @param userIds
     *            user identifiers
     */
    abstract protected void createUsers(long[] userIds);

    /**
     * Link the users in the database.
     * 
     * @param subscriptions
     *            subscriptions of the users
     * @return number of subscriptions
     */
    abstract protected long createSubscriptions(List<long[]> subscriptions);

    /**
     * Load the posts for each user.
     * 
     * @param numPosts
     *            number of posts per respective user, array has the same order
     *            as userIds
     * @return number of posts
     */
    abstract protected long loadPosts(int[] numPosts);

    /**
     * Add the posts to the database.
     * 
     * @param numPosts
     *            number of posts per respective user, array has the same order
     *            as userIds
     */
    abstract protected void createPosts(int[] numPosts);

    /**
     * Link the posts in the database.
     * 
     * @param numPosts
     *            number of posts per respective user, array has the same order
     *            as userIds
     */
    abstract protected void linkPosts(int[] numPosts);

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
}
