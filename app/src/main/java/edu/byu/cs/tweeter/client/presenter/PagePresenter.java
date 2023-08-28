package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.PageService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public abstract class PagePresenter<T> extends Presenter {

    private static final int PAGE_SIZE = 10;

    protected boolean hasMorePages;
    protected boolean isLoading = false;
    protected T lastItem;
    private User user;
    protected List<T> items;
    private AuthToken authToken;


    public interface PagedView <T> extends View {
        void setLoading(boolean value);
        void addItems(List<T> items);
        void navigateToUser(User user);
    }

    public PagePresenter(PagedView view, User targetUser, AuthToken authToken){
        super(view);
        user = targetUser;
        this.authToken = authToken;
    }

    public abstract void getItems(AuthToken authToken, User user, int limit, T lastStatus, PageService.GetItemsObserver observer);

    public void loadMoreItems(boolean value){
        if(value){
            if(!isLoading){
                isLoading = true;
                ((PagedView) this.view).setLoading(isLoading);
                getItems( authToken, user, PAGE_SIZE,lastItem, (PageService.GetItemsObserver)this);
            }
        }

        else{
            if(!isLoading && hasMorePages){
                isLoading = true;
                ((PagedView) this.view).setLoading(isLoading);
                getItems(authToken, user, PAGE_SIZE,lastItem, (PageService.GetItemsObserver)this);
            }
        }
    }

    public void gotoUser(String alias) {
        new UserService().getUser(alias, authToken, (UserService.getUserObserver) this);
    }
}
