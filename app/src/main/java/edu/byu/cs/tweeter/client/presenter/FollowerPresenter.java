package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.PageService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class FollowerPresenter extends PagePresenter<User> implements FollowService.getFollowersObserver, UserService.getUserObserver {

    public FollowerPresenter(FollowerView view, AuthToken authToken, User targetUser) {
        super(view, targetUser, authToken);
    }

    @Override
    public void getItems(AuthToken authToken, User user, int limit, User lastStatus, PageService.GetItemsObserver observer) {
        new FollowService().getFollowers(authToken, user, limit, lastItem, this);
    }

    public void nullChecker(User user) {
        if (user == null) {
            ((FollowerView) this.view).logger("user is null!");
        }
        if (user != null && user.getImageBytes() == null) {
            ((FollowerView) this.view).logger("image bytes are null");
        }
    }

    public interface FollowerView extends PagePresenter.PagedView<User> {
        void logger(String message);
    }

    @Override
    public void getItemsSucceeded(List<User> items, boolean hasMorePages, User lastItem) {
        this.items = items;
        this.hasMorePages = hasMorePages;
        this.lastItem = lastItem;
        isLoading = false;

        ((FollowerView)view).setLoading(isLoading);
        ((FollowerView)view).addItems(items);
    }

    @Override
    public void handleFailed(String message) {
        isLoading = false;

        ((FollowerView)view).setLoading(isLoading);
        ((FollowerView)view).displayErrorMessage(message);
    }

    @Override
    public void handleException(Exception ex) {
        isLoading = false;

        ((FollowerView)view).setLoading(isLoading);
        ((FollowerView)view).displayErrorMessage(ex.getMessage());
    }

    @Override
    public void getUserSucceed(User user) {
        ((FollowerView)view).displayInfoMessage("Getting user's profile...");
        ((FollowerView)view).navigateToUser(user);

    }




}
