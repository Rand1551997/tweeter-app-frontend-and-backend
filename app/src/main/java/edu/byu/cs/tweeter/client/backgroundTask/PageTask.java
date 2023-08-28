package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import java.io.Serializable;
import java.util.List;

public abstract class PageTask<T> extends AuthenticatedTask {
    private static final String LOG_TAG = "GetFeedTask";

    public static final String ITEMS_KEY = "statuses";
    public static final String MORE_PAGES_KEY = "more-pages";


    /**
     * Auth token for logged-in user.
     */
    //protected AuthToken authToken;
    /**
     * The user whose feed is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    protected User targetUser;
    /**
     * Maximum number of statuses to return (i.e., page size).
     */
    protected int limit;
    /**
     * The last status returned in the previous page of results (can be null).
     * This allows the new page to begin where the previous page ended.
     */
    protected T lastItem;

    protected List<T> items;

    protected boolean hasMorePages;


    public PageTask(AuthToken authToken, User targetUser, int limit, T lastItem,
                       Handler messageHandler) {
        super(authToken, messageHandler);
        this.targetUser = targetUser;
        this.limit = limit;
        this.lastItem = lastItem;
    }


    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(ITEMS_KEY, (Serializable) this.items);
        msgBundle.putBoolean(MORE_PAGES_KEY, hasMorePages);
    }
}

