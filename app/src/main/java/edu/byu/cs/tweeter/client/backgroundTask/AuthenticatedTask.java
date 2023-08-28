package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import edu.byu.cs.tweeter.model.domain.AuthToken;

public abstract class AuthenticatedTask extends BackgroundTask{

    private static final String LOG_TAG = "AuthenticatedTask";

    /**
     * Auth token for logged-in user.
     */
    protected AuthToken authToken;
    /**
     * Message handler that will receive task results.
     */
    protected Handler messageHandler;

    protected AuthenticatedTask(AuthToken authToken, Handler messageHandler) {
        super(messageHandler);
        this.authToken = authToken;
    }
}
