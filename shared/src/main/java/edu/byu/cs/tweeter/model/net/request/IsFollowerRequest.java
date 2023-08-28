package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class IsFollowerRequest {
    public IsFollowerRequest()
    {

    }
    public IsFollowerRequest(AuthToken token, String follower, String followee)
    {
        this.followee = followee;
        this.follower = follower;
        this.token = token;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    private String follower;

    public String getFollowee() {
        return followee;
    }

    public void setFollowee(String followee) {
        this.followee = followee;
    }

    private String followee;

    public AuthToken getToken() {
        return token;
    }

    public void setToken(AuthToken token) {
        this.token = token;
    }

    private AuthToken token;
}
