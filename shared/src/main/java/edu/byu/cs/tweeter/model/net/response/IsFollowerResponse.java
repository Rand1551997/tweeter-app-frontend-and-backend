package edu.byu.cs.tweeter.model.net.response;

public class IsFollowerResponse {

    private boolean isFollower;

    public IsFollowerResponse(boolean isFollower, String message, boolean success) {
        this.isFollower = isFollower;
        this.message = message;
        this.success = success;
    }

    public boolean isFollower() {
        return isFollower;
    }

    public void setFollower(boolean follower) {
        isFollower = follower;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;
    public IsFollowerResponse()
    {

    }
    public IsFollowerResponse(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }
}
