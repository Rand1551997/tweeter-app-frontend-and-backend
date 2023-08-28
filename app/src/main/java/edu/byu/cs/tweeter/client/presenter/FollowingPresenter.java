package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.PageService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class FollowingPresenter extends PagePresenter<User> implements UserService.getUserObserver, FollowService.getFollowingObserver{

    public interface FollowingView extends PagePresenter.PagedView<User> {
    }

    public FollowingPresenter(FollowingView view, AuthToken authToken, User targetUser) {
        super(view, targetUser, authToken);
    }

    @Override
    public void getUserSucceed(User user) {
        ((FollowingView)view).displayInfoMessage("Getting user's profile...");
        ((FollowingView)view).navigateToUser(user);

    }

    @Override
    public void getItemsSucceeded(List<User> items, boolean hasMorePages, User lastItem) {
        this.hasMorePages = hasMorePages;
        this.lastItem = lastItem;
        this.isLoading = false;

        ((FollowingView)view).setLoading(isLoading);
        ((FollowingView)view).addItems(items);
    }

    @Override
    public void handleFailed(String message) {
        ((FollowingView)view).displayErrorMessage("Failed to get Following or User Profile: " + message);
        isLoading = false;
        ((FollowingView)view).setLoading(isLoading);

    }

    @Override
    public void handleException(Exception ex) {
        ((FollowingView)view).displayErrorMessage("Failed to get Following or User Profile because of exception: " + ex.getMessage());
        isLoading = false;
        ((FollowingView)view).setLoading(isLoading);
    }


    @Override
    public void getItems(AuthToken authToken, User user, int limit, User lastStatus, PageService.GetItemsObserver observer) {
        new FollowService().getFollowing(authToken, user, limit, lastItem, this);
    }
}
