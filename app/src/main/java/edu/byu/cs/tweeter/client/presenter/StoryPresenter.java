package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.PageService;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class StoryPresenter extends PagePresenter<Status> implements UserService.getUserObserver, StoryService.getStoryObserver {


    @Override
    public void getItemsSucceeded(List<Status> items, boolean hasMorePages, Status lastItem) {
        this.items = items;
        this.hasMorePages = hasMorePages;
        this.lastItem = lastItem;

        isLoading = false;
        ((StoryView)view).setLoading(isLoading);
        ((StoryView)view).addItems(items);
    }

    @Override
    public void handleFailed(String message) {
        isLoading = false;
        ((StoryView)view).setLoading(isLoading);
        ((StoryView)view).displayErrorMessage("Failed to get feed/user's profile: " + message);
    }

    @Override
    public void handleException(Exception ex) {
        isLoading = false;
        ((StoryView)view).setLoading(isLoading);
        ((StoryView)view).displayErrorMessage("Failed to get feed/user's profile because of exception: " + ex.getMessage());
    }

    @Override
    public void getUserSucceed(User user) {
        ((StoryView)view).displayInfoMessage("Getting user's profile...");
        ((StoryView)view).navigateToUser(user);

    }

    @Override
    public void getItems(AuthToken authToken, User user, int limit, Status lastStatus, PageService.GetItemsObserver observer) {
        new StoryService().getStory(authToken, user, limit, lastItem, this);
    }

    public interface StoryView extends PagedView<Status>{
    }

    public StoryPresenter(StoryView view, User user, AuthToken authToken){
        super(view, user,authToken);
    }
}
