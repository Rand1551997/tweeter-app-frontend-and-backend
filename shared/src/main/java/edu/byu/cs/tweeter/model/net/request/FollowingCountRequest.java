package edu.byu.cs.tweeter.model.net.request;

public class FollowingCountRequest {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
    public FollowingCountRequest(){}
    public FollowingCountRequest(String userName)
    {
        this.userName = userName;
    }
}
