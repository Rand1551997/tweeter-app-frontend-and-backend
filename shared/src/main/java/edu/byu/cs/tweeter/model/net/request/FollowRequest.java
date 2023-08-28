package edu.byu.cs.tweeter.model.net.request;


public class FollowRequest {

    private String userLoggedIn;
    private String followeeAlias;
    private String followeeName;
    private String followerName;
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public FollowRequest()
    {
        //Follower is the person following example A: follower -> B: followee

    }
    public FollowRequest(String followerAlias, String followerName, String followeeAlias, String followeeName)
    {
        this.userLoggedIn = followerAlias;
        this.followeeAlias = followeeAlias;
        this.followerName = followerName;
        this.followeeName = followeeName;
    }
    public String getUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(String userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }


    public String getFolloweeAlias() {
        return followeeAlias;
    }

    public void setFolloweeAlias(String followeeAlias) {
        this.followeeAlias = followeeAlias;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }



    public String getFolloweeName() {
        return followeeName;
    }

    public void setFolloweeName(String followeeName) {
        this.followeeName = followeeName;
    }
}
