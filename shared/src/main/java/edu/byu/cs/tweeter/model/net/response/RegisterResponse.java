package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.Objects;

public class RegisterResponse {

    private User user;
    private AuthToken authToken;

    public RegisterResponse(User user, AuthToken authToken, boolean success, String message)
    {
        this.success = success;
        this.message = message;
        this.user = user;
        this.authToken = authToken;
    }

    public User getUser(){return user;}
    public AuthToken getAuthToken(){return authToken;}

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


    public RegisterResponse(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterResponse response = (RegisterResponse) o;
        return success == response.success &&
                Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message);
    }
}
