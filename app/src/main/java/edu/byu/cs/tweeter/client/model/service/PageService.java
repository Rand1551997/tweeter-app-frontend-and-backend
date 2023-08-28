package edu.byu.cs.tweeter.client.model.service;

import android.os.Message;
import edu.byu.cs.tweeter.client.backgroundTask.GetFeedTask;

import java.util.List;

public abstract class PageService <T> extends ExecuteClass{

    public interface GetItemsObserver <T> extends ServiceObserver{
        void getItemsSucceeded(List<T> items, boolean hasMorePages, T lastItem);
    }

    protected void getItems(Message msg, GetItemsObserver getItemsObserver){

        List<T> items = (List<T>) msg.getData().getSerializable(GetFeedTask.ITEMS_KEY);
        boolean hasMorePages = msg.getData().getBoolean(GetFeedTask.MORE_PAGES_KEY);
        T lastItem = (items.size() > 0) ? items.get(items.size() - 1) : null;
        getItemsObserver.getItemsSucceeded(items, hasMorePages, lastItem);

    }

}
