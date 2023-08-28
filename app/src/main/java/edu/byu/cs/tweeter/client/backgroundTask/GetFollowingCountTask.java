package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;

import java.io.IOException;

/**
 * Background task that queries how many other users a specified user is following.
 */
public class GetFollowingCountTask extends GetCountTask{

    private static final String LOG_TAG = "GetFollowingCountTask";
    private static final String URL_PATH = "/getfollowingcount";


    public GetFollowingCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(authToken, targetUser, messageHandler);
    }

    @Override
    protected boolean runTask() {
       FollowingCountRequest request = new FollowingCountRequest(targetUser.getAlias());
        try {
            FollowingCountResponse response = new ServerFacade().getFollowingCount(request,URL_PATH);
            this.count = response.getCount();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }

        return false;

    }
}
