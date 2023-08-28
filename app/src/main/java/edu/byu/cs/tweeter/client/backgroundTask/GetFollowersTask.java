package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.client.util.Pair;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PageTask<User> {
    private static final String LOG_TAG = "GetFollowersTask";
    private static final String URL_PATH = "/getfollowers";


    public GetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(authToken,targetUser,limit,lastFollower,messageHandler);
    }

    @Override
   protected boolean runTask() {

        boolean success = false;
        FollowersRequest request = new FollowersRequest();
        request.setFollowerAlias(targetUser.getAlias());
        if(this.lastItem != null) {
            request.setLastFolloweeAlias(this.lastItem.getAlias());
        }
        request.setLimit(limit);

        try {
            FollowersResponse response = new ServerFacade().getFollowers(request,URL_PATH);
            this.items = response.getFollowers();
            this.hasMorePages = response.getHasMorePages();
            success = response.isSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }

        for (User u : this.items) {
            try {
                BackgroundTaskUtils.loadImage(u);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return success;
    }
}
