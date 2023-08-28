package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.User;

import java.util.Objects;

public class FollowerCountResponse {
    private boolean success;
    private int count;
    private String message;

    public FollowerCountResponse(){}

    public FollowerCountResponse(int count,boolean success,String message)
    {
        this.count = count;
        this.success = success;
        this.message = message;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSuccess(){return success;}
    public String getMessage(){return message;}


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowerCountResponse response = (FollowerCountResponse) o;
        return count == response.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }
}
