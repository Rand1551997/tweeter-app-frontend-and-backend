package edu.byu.cs.tweeter.client.model.service;

import android.os.Message;
import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedService extends PageService{


    public void getFeed(AuthToken authToken, User user, int limit, Status lastStatus, GetFeedObserver observer){
        execute(new GetFeedTask(authToken, user, limit, lastStatus, new GetFeedHandler(observer)));
    }

    public interface GetFeedObserver extends PageService.GetItemsObserver<Status>{}

    private class GetFeedHandler extends BackgroundTaskHandler {

        public GetFeedHandler(GetFeedObserver observer) {
            super(observer);
        }
        @Override
        protected void handleSuccess(Message msg) {
            getItems(msg, (GetItemsObserver) this.observer);

        }
    }
}
