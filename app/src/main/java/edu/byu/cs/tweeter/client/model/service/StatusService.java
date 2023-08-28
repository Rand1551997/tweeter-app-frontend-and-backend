package edu.byu.cs.tweeter.client.model.service;

import android.os.Message;
import edu.byu.cs.tweeter.client.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StatusService extends PageService{

    public interface PostStatusObserver extends ServiceObserver{
        void postStatusSucceeded();
    }


    public void postStatus(Status post, AuthToken authToken, PostStatusObserver observer) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        execute(new PostStatusTask(authToken,post, new PostStatusHandler(observer)));

    }

    private class PostStatusHandler extends BackgroundTaskHandler {

        public PostStatusHandler(PostStatusObserver observer){
            super(observer);
        }

        @Override
        protected void handleSuccess(Message msg) {
            ((PostStatusObserver)this.observer).postStatusSucceeded();
        }
    }
}
