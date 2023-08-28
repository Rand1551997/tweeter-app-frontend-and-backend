package edu.byu.cs.tweeter.model.net.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetFeedResponse extends PagedResponse {
    public GetFeedResponse()
    {

    }
    public GetFeedResponse(boolean success, boolean hasMorePages) {
        super(success, hasMorePages);
    }

    public GetFeedResponse(User user, int limit, Status lastStatus, List<Status> statuses)
    {
        this.feedList = statuses;
        this.limit = limit;
        this.lastStatus = lastStatus;
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    private int limit;

    public Status getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }

    private Status lastStatus;

    public List<Status> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<Status> feedList) {
        this.feedList = feedList;
    }

    private List<Status> feedList;

    @Override
    public String toString() {
        return "GetFeedResponse{" +
                "user=" + user +
                ", limit=" + limit +
                ", lastStatus=" + lastStatus +
                ", feedList=" + feedList +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
