package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class PostStatusRequest {

    private Status statusStory;
    private User currUser;
    private AuthToken authToken;

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    public PostStatusRequest(Status status) {
        this.statusStory = status;
    }

    public void setStatusStory(Status statusStory) {
        this.statusStory = statusStory;
    }

    public void setCurrUser(User currUser) {
        this.currUser = currUser;
    }

    public PostStatusRequest(Status status, User currUser) {
        this.statusStory = status;
        this.currUser = currUser;
    }

    @Override
    public String toString() {
        return "PostStatusRequest{" +
                "statusStory=" + statusStory +
                ", currUser=" + currUser +
                '}';
    }

    public Status getStatusStory(){
        return statusStory;
    }


    public PostStatusRequest() {}

    public User getCurrUser() {
        return currUser;
    }
}
