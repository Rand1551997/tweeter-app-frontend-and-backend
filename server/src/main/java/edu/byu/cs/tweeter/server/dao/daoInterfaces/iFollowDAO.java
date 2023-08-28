package edu.byu.cs.tweeter.server.dao.daoInterfaces;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ResultPage;
import edu.byu.cs.tweeter.server.util.Pair;

import java.util.List;

public interface iFollowDAO {

    int getFollowerCount(String alias);

    public int getFollowingCount(String alias);

    public ResultPage getFollowers(String userAlias, String lastFolloweeAlias,int limit);

    public ResultPage getFollowing(String userAlias, String lastFolloweeAlias,int limit);

    public boolean isFollowing(String loggedInUser, String targetUser);

    boolean follow(User loggedInUser, User followUser);

    public boolean unfollow(String loggedInUser, String UserBeingUnfollowed);

    public List<User> getAllFollowers(String alias);

}
