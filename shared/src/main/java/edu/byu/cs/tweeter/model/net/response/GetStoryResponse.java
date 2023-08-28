package edu.byu.cs.tweeter.model.net.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class GetStoryResponse extends PagedResponse {

    public GetStoryResponse(boolean hasMorePages, List<Status> items, boolean b, String got_stories) {
        this.storyList = items;
        this.setHasMorePages(hasMorePages);
        this.success = b;
        this.message = got_stories;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }

    private Status lastStatus;
    private User user;
    private AuthToken authToken;
    private int limit;


    public List<Status> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Status> storyList) {
        this.storyList = storyList;
    }

    private List<Status> storyList;

    public GetStoryResponse(boolean success, boolean hasMorePages) {
        super(success, hasMorePages);
    }

    public GetStoryResponse(AuthToken authToken, User user, int limit, Status lastStatus, List<Status> statuses)
    {
        this.authToken = authToken;
        this.user = user;
        this.limit = limit;
        this.lastStatus = lastStatus;
        this.storyList = statuses;

    }

    public GetStoryResponse()
    {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetStoryResponse response = (GetStoryResponse) o;
        return limit == response.limit &&
                Objects.equals(lastStatus, response.lastStatus) &&
                Objects.equals(user, response.user) &&
                Objects.equals(authToken, response.authToken) &&
                Objects.equals(storyList, response.storyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastStatus, user, authToken, limit, storyList);
    }
}
