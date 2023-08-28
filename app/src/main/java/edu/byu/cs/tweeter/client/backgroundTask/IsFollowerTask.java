package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.util.Random;

import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.IsFollowerRequest;
import edu.byu.cs.tweeter.model.net.response.IsFollowerResponse;

/**
 * Background task that determines if one user is following another.
 */

//TODO:
    //Should I add a bundle and why
    //String IS follower KEY should I remove it
    // WHat about the follower and followee?
    //Should runTask be like this?
public class IsFollowerTask extends AuthenticatedTask{
    private static final String LOG_TAG = "IsFollowerTask";
    private static final String URL_PATH = "/follower";

    public static final String IS_FOLLOWER_KEY = "is-follower";

    /**
     * The alleged follower.
     */
    private User follower;
    /**
     * The alleged followee.
     */
    private User followee;

    private boolean isFollower;


    public IsFollowerTask(AuthToken authToken, User follower, User followee, Handler messageHandler) {
        super(authToken, messageHandler);
        this.follower = follower;
        this.followee = followee;
    }

    @Override
    protected boolean runTask() {
        IsFollowerRequest request = new IsFollowerRequest();
        request.setFollower(follower.getAlias());
        request.setFollowee(followee.getAlias());
        request.setToken(authToken);
        isFollower = false;
        boolean success = false;
        try {
            IsFollowerResponse response = new ServerFacade().isFollower(request,URL_PATH);
            isFollower = response.isFollower();
            success = response.isSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putBoolean(IS_FOLLOWER_KEY, isFollower);
    }
}
