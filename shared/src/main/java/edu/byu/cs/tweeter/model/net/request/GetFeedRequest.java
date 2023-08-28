package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetFeedRequest {
    private String userName;
    private Integer limit;
    private Status lastStatus;
    private AuthToken authToken;

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "GetFeedRequest{" +
                "userName='" + userName + '\'' +
                ", limit=" + limit +
                ", lastStatus=" + lastStatus +
                '}';
    }

    public GetFeedRequest(String userName, Integer limit, Status lastStatus) {
        this.userName = userName;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Status getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }

    public GetFeedRequest()
    {

    }
    public GetFeedRequest(String userName)
    {
        this.userName = userName;
    }
}
