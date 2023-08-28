package edu.byu.cs.tweeter.client.model.service;

import android.os.Message;
import edu.byu.cs.tweeter.client.backgroundTask.*;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService extends PageService<User>{

    public interface getFollowersObserver extends PageService.GetItemsObserver<User>{}

    public void getFollowers(AuthToken authToken, User user, int limit, User lastFollower, getFollowersObserver observer){
        execute( new GetFollowersTask(authToken,user, limit, lastFollower, new GetFollowersHandler(observer)));

    }

    private class GetFollowersHandler extends BackgroundTaskHandler {

        public GetFollowersHandler(getFollowersObserver observer) {
            super(observer);
        }

        @Override
        protected void handleSuccess(Message msg) {
            getItems(msg, (GetItemsObserver)this.observer);
        }
    }



    public interface getFollowingObserver extends PageService.GetItemsObserver<User>{
    }

    public void getFollowing(AuthToken authToken, User targetUser, int limit, User lastFollowee, FollowService.getFollowingObserver observer){

        execute( new GetFollowingTask(authToken, targetUser, limit, lastFollowee, new GetFollowingHandler(observer)));
    }

    private class GetFollowingHandler extends BackgroundTaskHandler {

        public GetFollowingHandler(getFollowingObserver observer){
            super(observer);
        }

        @Override
        protected void handleSuccess(Message msg) {
            getItems(msg, (GetItemsObserver)this.observer);
        }
    }

    public interface isFollowerObserver extends ServiceObserver{
        void isFollower(boolean value);
    }

    public void isFollower(AuthToken authToken, User user, User selectedUser, isFollowerObserver observer){
        execute(new IsFollowerTask(authToken, user, selectedUser, new IsFollowerHandler(observer)));
    }

    private class IsFollowerHandler extends BackgroundTaskHandler {

        public IsFollowerHandler(isFollowerObserver observer){
            super(observer);
        }

        @Override
        protected void handleSuccess(Message msg) {
            ((isFollowerObserver)this.observer).isFollower(msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY));
        }
    }

    public interface followOrUnfollowObserver extends ServiceObserver{
        void followOrUnfollowSucceed(String message);
        void updateUsersAndFollowers(boolean removed);
        void returnFollowerCount(int num);
        void returnFollowingCount(int num);
    }

    public void followOrUnfollow(boolean unfollow,AuthToken authToken, User selectedUser ,followOrUnfollowObserver observer){

        if (unfollow) {
            execute(new UnfollowTask(authToken,selectedUser, new UnfollowHandler(observer)));
            observer.followOrUnfollowSucceed("Removing " + selectedUser.getName() + "...");

        } else {
            execute(new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                    selectedUser, new FollowHandler(observer)));
            observer.followOrUnfollowSucceed("Adding " + selectedUser.getName() + "...");
        }
    }

    // FollowHandler

    private class FollowHandler extends BackgroundTaskHandler {

        public FollowHandler(followOrUnfollowObserver observer){
            super(observer);
        }

        @Override
        protected void handleSuccess(Message msg) {

            //
            //THE VALUE HERE IS FALSE
            ((followOrUnfollowObserver)this.observer).updateUsersAndFollowers(false);
        }
    }

    // UnfollowHandler

    private class UnfollowHandler extends BackgroundTaskHandler {

        public UnfollowHandler( followOrUnfollowObserver observer){
            super(observer);
        }

        //TODO:Fix this
        // Follow or Unfollow
        @Override
        protected void handleSuccess(Message msg) {
            ((followOrUnfollowObserver)this.observer).updateUsersAndFollowers(true);
        }
    }

    // GetFollowersCountHandler
    public void getFollowerCount(AuthToken authToken, User selectedUser, followOrUnfollowObserver observer) {
        execute(new GetFollowersCountTask(authToken, selectedUser, new GetFollowersCountHandler(observer)));
    }

    private class GetFollowersCountHandler extends BackgroundTaskHandler {

        public GetFollowersCountHandler( followOrUnfollowObserver observer){
            super(observer);
        }

        @Override
        protected void handleSuccess(Message msg) {
            ((followOrUnfollowObserver)this.observer).returnFollowerCount(msg.getData().getInt(GetFollowersCountTask.COUNT_KEY));
        }
    }

    // GetFollowingCountHandler

    public void getFollowingCount(AuthToken authToken, User selectedUser, followOrUnfollowObserver observer) {
        execute(new GetFollowingCountTask(authToken, selectedUser, new GetFollowingCountHandler(observer)));
    }

    private class GetFollowingCountHandler extends BackgroundTaskHandler {

        public GetFollowingCountHandler( followOrUnfollowObserver observer){
            super(observer);
        }

        @Override
        protected void handleSuccess(Message msg) {
            ((followOrUnfollowObserver)this.observer).returnFollowingCount(msg.getData().getInt(GetFollowersCountTask.COUNT_KEY));
        }
    }

}
