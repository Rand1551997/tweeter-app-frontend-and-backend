package edu.byu.cs.tweeter.model.net.request;


import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;

public class GetStoryRequest {

    private String userName;
    private int limit;
    private Status lastStatus;
    private AuthToken authToken;

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString(){
        return "GetStoryRequest{" + "userName'" + userName + '\'' + ", limit=" + limit + ", lastStatus=" + lastStatus + '}';
    }

    public GetStoryRequest(String userName, int limit, Status lastStatus) {
        this.userName = userName;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public String getUserName() {
        return userName;
    }

    public int getLimit() {
        return limit;
    }

    public Status getLastStatus() { return lastStatus; }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setLastStatus(Status lastStatus) {
        this.lastStatus = lastStatus;
    }

    public GetStoryRequest () {}

    public GetStoryRequest(String userName)
    {
        this.userName = userName;
    }

}
