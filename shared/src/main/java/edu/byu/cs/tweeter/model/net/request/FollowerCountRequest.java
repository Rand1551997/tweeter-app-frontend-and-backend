package edu.byu.cs.tweeter.model.net.request;

public class FollowerCountRequest {
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
    public FollowerCountRequest(){}
    public FollowerCountRequest(String userName)
    {
        this.userName = userName;
    }
}
