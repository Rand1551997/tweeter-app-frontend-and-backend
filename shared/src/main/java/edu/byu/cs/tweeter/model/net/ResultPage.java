package edu.byu.cs.tweeter.model.net;

import java.util.List;

public class ResultPage<T> {

    private boolean hasMorePages;
    private List<T> items;
    private T lastItem;

    public ResultPage(boolean hasMorePages, List<T> items) {
        this.hasMorePages = hasMorePages;
        this.items = items;
    }


//    public ResultPage(boolean hasMorePages, List<T> items, T lastItem) {
//        this.hasMorePages = hasMorePages;
//        this.items = items;
//        this.lastItem = lastItem;
//    }

    public T getLastItem() {
        return lastItem;
    }

    public void setLastItem(T lastItem) {
        this.lastItem = lastItem;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }


    public boolean isHasMorePages() {
        return hasMorePages;
    }

    public void setHasMorePages(boolean hasMorePages) {
        this.hasMorePages = hasMorePages;
    }





}
