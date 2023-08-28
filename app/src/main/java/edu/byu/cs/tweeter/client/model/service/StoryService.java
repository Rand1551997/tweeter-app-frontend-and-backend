package edu.byu.cs.tweeter.client.model.service;

import android.os.Message;
import edu.byu.cs.tweeter.client.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryService extends PageService<Status>{

    public interface getStoryObserver extends PageService.GetItemsObserver<Status>{}

    public void getStory(AuthToken authToken, User user, int limit, Status lastStatus, getStoryObserver observer){
        execute(new GetStoryTask(authToken, user, limit, lastStatus, new GetStoryHandler(observer)));
    }

    private class GetStoryHandler extends BackgroundTaskHandler {

        public GetStoryHandler(getStoryObserver observer){
            super(observer);
        }

        @Override
        protected void handleSuccess(Message msg) {
            getItems(msg, (GetItemsObserver) this.observer);

        }

    }



}
