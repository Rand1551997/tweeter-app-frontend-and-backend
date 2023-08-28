package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.Status;

import java.util.List;

public class BatchStatusRequest {
    private String feed; //DON"T NEED
    private List<String> users;
    private String userWhoPosted;

    public String getUserWhoPosted() {
        return userWhoPosted;
    }

    public void setUserWhoPosted(String userWhoPosted) {
        this.userWhoPosted = userWhoPosted;
    }

    public BatchStatusRequest(){}

    public BatchStatusRequest(String feed, List<String> users, String userWhoPosted){
        this.feed = feed;
        this.users = users;
        this.userWhoPosted = userWhoPosted;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "BatchStatusRequest{" +
                "feed='" + feed + '\'' +
                ", users=" + users +
                ", userWhoPosted='" + userWhoPosted + '\'' +
                '}';
    }
}
