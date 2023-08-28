package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter extends Presenter implements StatusService.PostStatusObserver, FollowService.isFollowerObserver,
        FollowService.followOrUnfollowObserver, UserService.logoutObserver {

    private static final String LOG_TAG = "Main Presenter";

    protected User selectedUser;

    public User getUser(){
        return selectedUser;
    }

    @Override
    public void handleFailed(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void handleException(Exception ex) {
        view.displayErrorMessage(ex.getMessage());
    }

    @Override
    public void postStatusSucceeded() {
        view.displayInfoMessage("Posted Successfully! Post more :)");
    }


    public MainActivityPresenter(MainView view, User selectedUser){
        super(view);
        this.selectedUser = selectedUser;
    }

    @Override
    public void isFollower(boolean value) {
        ((MainView)this.view).changeFollowButton(value);
    }

    @Override
    public void followOrUnfollowSucceed(String message) {
        ((MainView)this.view).displayInfoMessage(message);
    }

    @Override
    public void updateUsersAndFollowers(boolean removed) {
        ((MainView)this.view).changeFollowButton(!removed);
    }

    public void getFollowersCount(AuthToken authToken, User selectedUser) {
        new FollowService().getFollowerCount(authToken, selectedUser, this);
    }

    public void getFollowingCount(AuthToken authToken, User selectedUser) {
        new FollowService().getFollowingCount(authToken, selectedUser, this);
    }

    @Override
    public void returnFollowerCount(int num) {
        ((MainView)this.view).setFollowerCount(num);
    }

    @Override
    public void returnFollowingCount(int num) {
        ((MainView)this.view).setFollowingCount(num);
    }



    public void setSelectedUser(User selectedUser){
        this.selectedUser = selectedUser;
    }

    @Override
    public void logoutSuccess() {
        Cache.getInstance().clearCache();
        ((MainView)this.view).cancelToast();
        ((MainView)this.view).logoutUser();
    }

    public StatusService getStatusService() {
        return new StatusService();
    }


    public interface MainView extends Presenter.View{
        void changeFollowButton(boolean isFollower);
        void logoutUser();
        void cancelToast();
        void setFollowingCount(int num);
        void setFollowerCount(int num);
    }


    public void postStatus(String post, User currentUser, AuthToken authToken){
        try {
            view.displayInfoMessage("Pending Status...");
            long millis=System.currentTimeMillis();
            Status newStatus = new Status(post, selectedUser, getFormattedDateTime(), parseURLs(post), parseMentions(post),millis);
            getStatusService().postStatus(newStatus,authToken, this);
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            view.displayErrorMessage("Failed to post the status because of exception: " + ex.getMessage());
        }
    }

    public void isFollowerr(AuthToken authToken, User user, User selectedUser){
        new FollowService().isFollower(authToken,user, selectedUser,this);
    }

    public void followOrUnfollow(boolean unfollow,AuthToken authToken, User selectedUser){
        new FollowService().followOrUnfollow(unfollow, authToken, selectedUser,this);
    }

    public void logoutUser(AuthToken authToken){
        new UserService().logout(authToken, this);
    }


    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    public List<String> parseURLs(String post) throws MalformedURLException {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    public List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    public int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }
}
