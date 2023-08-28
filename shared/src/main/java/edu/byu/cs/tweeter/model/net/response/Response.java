package edu.byu.cs.tweeter.model.net.response;

import java.io.Serializable;

/**
 * A base class for server responses.
 */
class Response implements Serializable {

    protected boolean success;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected String message;

    /**
     * Creates an instance with a null message.
     *
     * @param success the success message.
     */
    public Response(boolean success) {
        this(success, null);
    }

    public Response(){}
    /**
     * Creates an instance.
     *
     * @param success the success indicator.
     * @param message the error message.
     */
    Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Indicates whether the response represents a successful result.
     *
     * @return the success indicator.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * The error message for unsuccessful results.
     *
     * @return an error message or null if the response indicates a successful result.
     */
    public String getMessage() {
        return message;
    }
}
