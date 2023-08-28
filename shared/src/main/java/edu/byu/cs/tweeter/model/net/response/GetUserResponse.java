package edu.byu.cs.tweeter.model.net.response;

import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.User;

public class GetUserResponse {

    @Override
    public String toString() {
        return "GetUserResponse{" +
                "user=" + user +
                ", success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public boolean isSuccess(){return success;}
    public String getMessage(){return message;}

    private User user;
    private boolean success;
    private String message;

    public GetUserResponse( User user,boolean success,String message)
    {
        this.user = user;
        this.success = success;
        this.message = message;

    }
    public GetUserResponse(User user)
    {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetUserResponse response = (GetUserResponse) o;
        return Objects.equals(user, response.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
