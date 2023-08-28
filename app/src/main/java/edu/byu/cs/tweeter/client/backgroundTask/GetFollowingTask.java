package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.client.util.Pair;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.response.FollowResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;

/**
 * Background task that retrieves a page of other users being followed by a specified user.
 */
public class GetFollowingTask extends PageTask<User> {
    private static final String LOG_TAG = "GetFollowingTask";
    private static final String URL_PATH = "/getfollowing";

    public GetFollowingTask(AuthToken authToken, User targetUser, int limit, User lastFollowee,
                            Handler messageHandler) {
        super(authToken,targetUser,limit,lastFollowee,messageHandler);
    }

    @Override
    protected boolean runTask() {

        boolean success = false;
        FollowingRequest request = new FollowingRequest();
        request.setFollowerAlias(targetUser.getAlias());
        if(this.lastItem != null) {
            request.setLastFolloweeAlias(this.lastItem.getAlias());
        }
        request.setLimit(limit);

        try {
            FollowingResponse response = new ServerFacade().getFollowees(request,URL_PATH);
            this.items = response.getFollowees();
            this.hasMorePages = response.getHasMorePages();
            success = response.isSuccess();
            for (User u : this.items){
                BackgroundTaskUtils.loadImage(u);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }

        return success;
    }

}
