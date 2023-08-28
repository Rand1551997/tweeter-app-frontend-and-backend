package edu.byu.cs.tweeter.model.net.response;

public class PostStatusResponse {

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

    public PostStatusResponse ()
    {}
    public PostStatusResponse(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

}
