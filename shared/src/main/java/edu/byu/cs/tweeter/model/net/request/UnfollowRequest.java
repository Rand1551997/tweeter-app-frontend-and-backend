package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class UnfollowRequest{

    public String getUserBeingUnfollowed() {
        return userBeingUnfollowed;
    }

    public void setUserBeingUnfollowed(String userBeingUnfollowed) {
        this.userBeingUnfollowed = userBeingUnfollowed;
    }

    private String userBeingUnfollowed;

    public String getUserLoggedIn() {
        return userLoggedIn;
    }

    public void setUserLoggedIn(String userLoggedIn) {
        this.userLoggedIn = userLoggedIn;
    }

    private String userLoggedIn;
    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    private AuthToken authToken;
    public UnfollowRequest(){}
    public UnfollowRequest(String userLoggedIn, String userBeingFollowed, AuthToken authToken)
    {
        this.userLoggedIn = userLoggedIn;
        this.userBeingUnfollowed = userBeingFollowed;
        this.authToken = authToken;
    }
}
