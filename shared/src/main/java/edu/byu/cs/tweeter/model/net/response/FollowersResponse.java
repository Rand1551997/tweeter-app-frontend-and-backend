package edu.byu.cs.tweeter.model.net.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowersResponse extends PagedResponse {

    private List<User> followers;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful. Sets the
     * success and more pages indicators to false.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowersResponse(String message) {
        super(true, "success", false);
    }


    public FollowersResponse(List<User> followers, boolean hasMorePages) {
        super(true,"success" , hasMorePages);
        this.followers = followers;
    }

    public FollowersResponse(List<User> users)
    {
        super(true, "success", true);
        this.followers = users;
    }


    /**
     * Returns the followees for the corresponding request.
     *
     * @return the followees.
     */
    public List<User> getFollowers() {
        return followers;
    }

    public FollowersResponse(){}

    @Override
    public boolean equals(Object param) {
        if (this == param) {
            return true;
        }

        if (param == null || getClass() != param.getClass()) {
            return false;
        }

        FollowersResponse that = (FollowersResponse) param;

        return (Objects.equals(followers, that.getFollowers()) &&
                Objects.equals(this.getMessage(), that.getMessage()) &&
                this.isSuccess() == that.isSuccess());
    }

    @Override
    public int hashCode() {
        return Objects.hash(followers);
    }
}
