package edu.byu.cs.tweeter.model.net.request;

public class FollowersRequest
{
    public FollowersRequest()
    {}

    public String getFollowerAlias()
    {
        return followerAlias;
    }

    public void setFollowerAlias(String followerAlias)
    {
        this.followerAlias = followerAlias;
    }

    private String followerAlias;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    private int limit;

    public String getLastFolloweeAlias()
    {
        return lastFolloweeAlias;
    }

    public void setLastFolloweeAlias(String lastFolloweeAlias)
    {
        this.lastFolloweeAlias = lastFolloweeAlias;
    }

    private String lastFolloweeAlias;
}
