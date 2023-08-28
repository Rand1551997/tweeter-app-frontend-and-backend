package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import java.io.IOException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.client.util.Pair;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PageTask<Status> {
    private static final String LOG_TAG = "GetFeedTask";
    private static final String URL_PATH = "/getfeed";


    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(authToken, targetUser, limit, lastStatus, messageHandler);
    }

    @Override
    protected boolean runTask() {
        boolean success = false;
        GetFeedRequest request = new GetFeedRequest(targetUser.getAlias());
        request.setLastStatus(this.lastItem);
        request.setAuthToken(authToken);
        request.setLimit(this.limit);
        System.out.println(request);

        try {
            GetFeedResponse response = new ServerFacade().getFeed(request,URL_PATH);
            this.items = response.getFeedList();
            this.hasMorePages = response.getHasMorePages();
            System.out.println(response);
            success = response.isSuccess();
            for (Status s : items) {
                BackgroundTaskUtils.loadImage(s.getUser());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return success;
    }

}
