package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;

import java.io.IOException;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends AuthenticatedTask{
    private static final String LOG_TAG = "FollowTask";
    private static final String URL_PATH = "/follow";

    /**
     * The user that is being followed.
     */
    private User followee;

    public FollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        this.followee = followee;
    }

    @Override
    protected boolean runTask() {
        FollowRequest request = new FollowRequest();
        request.setAuthToken(authToken.getToken());
        request.setFolloweeAlias(followee.getAlias());
        request.setFolloweeName(followee.getName());
        request.setFollowerName(Cache.getInstance().getCurrUser().getName());
        request.setUserLoggedIn(Cache.getInstance().getCurrUser().getAlias());
        try {
            FollowResponse response = new ServerFacade().follow(request, URL_PATH);
            return response.isSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }

        return false;

    }

}
