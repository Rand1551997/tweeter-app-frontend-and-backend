package edu.byu.cs.tweeter.model.net.response;

public class UnfollowResponse {
    public UnfollowResponse()
    {

    }
    public UnfollowResponse(boolean success, String message)
    {
        this.message = message;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

}
