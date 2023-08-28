package edu.byu.cs.tweeter.model.net.response;

import edu.byu.cs.tweeter.model.domain.User;

import java.util.Objects;

public class FollowingCountResponse {
    public FollowingCountResponse(){}
    public FollowingCountResponse(int count,boolean success,String message)
    {
        this.count = count;
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess(){return success;}
    public String getMessage(){return message;}

    private boolean success;
    private String message;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowingCountResponse response = (FollowingCountResponse) o;
        return count == response.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }
}
