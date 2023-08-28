package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.PageService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class FeedPresenter extends PagePresenter<Status> implements UserService.getUserObserver, FeedService.GetFeedObserver {


    @Override
    public void getItems(AuthToken authToken, User user, int limit, Status lastStatus, PageService.GetItemsObserver observer) {
        new FeedService().getFeed(authToken, user, limit, lastItem, this);
    }

    //View
    public interface FeedView extends PagePresenter.PagedView<Status> {
    }

    public FeedPresenter(FeedView view, User user, AuthToken authToken) {
        super(view, user, authToken);
    }

    @Override
    public void getItemsSucceeded(List<Status> items, boolean hasMorePages, Status lastItem) {
        this.lastItem = lastItem;
        this.hasMorePages = hasMorePages;
        this.isLoading = false;

        ((FeedView) this.view).setLoading(false);
        ((FeedView) this.view).addItems(items);
    }

    @Override
    public void handleFailed(String message) {
        isLoading = false;
        ((FeedView) this.view).setLoading(isLoading);
        ((FeedView) this.view).displayErrorMessage("Failed to get feed or user's profile: " + message);
    }

    @Override
    public void handleException(Exception ex) {
        isLoading = false;
        ((FeedView) this.view).setLoading(isLoading);
        ((FeedView) this.view).displayErrorMessage("Failed to get feed or user's profile because of exception: " + ex.getMessage());
    }

    @Override
    public void getUserSucceed(User user) {
        ((FeedView) this.view).displayInfoMessage("Getting user's profile...");
        ((FeedView) this.view).navigateToUser(user);

    }

}
