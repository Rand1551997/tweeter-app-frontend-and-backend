package edu.byu.cs.tweeter.server.dao.dummy;

import java.util.List;
import java.util.Random;

import javax.xml.transform.Result;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ResultPage;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iFollowDAO;
import edu.byu.cs.tweeter.server.util.FakeData;
import edu.byu.cs.tweeter.server.util.Pair;

public class FollowDAO implements iFollowDAO {
    @Override
    public int getFollowerCount(String alias) {
        return 20;
    }

    @Override
    public int getFollowingCount(String alias) {
        return 20;
    }

    @Override
    public ResultPage getFollowers(String userAlias, String lastFolloweeAlias, int limit) {
        User userAliasFake = new FakeData().findUserByAlias(userAlias);
        User lastFolloweeAliasFake = new FakeData().findUserByAlias(userAlias);
        Pair<List<User>, Boolean> pageOfUsers = new FakeData().getPageOfUsers(lastFolloweeAliasFake, limit, userAliasFake);
        return new ResultPage(pageOfUsers.getSecond(), pageOfUsers.getFirst());
    }

    @Override
    public ResultPage getFollowing(String userAlias, String lastFolloweeAlias, int limit) {
        User userAliasFake = new FakeData().findUserByAlias(userAlias);
        User lastFolloweeAliasFake = new FakeData().findUserByAlias(userAlias);
        Pair<List<User>, Boolean> pageOfUsers = new FakeData().getPageOfUsers(lastFolloweeAliasFake, limit, userAliasFake);
        return new ResultPage(pageOfUsers.getSecond(), pageOfUsers.getFirst());
    }

    @Override
    public boolean isFollowing(String loggedInUser, String targetUser) {
        return (new Random().nextInt() > 0);
    }

    @Override
    public boolean follow(User loggedInUser, User followUser) {
        return true;
    }

    @Override
    public boolean unfollow(String loggedInUser, String UserBeingUnfollowed) {
        return true;
    }

    @Override
    public List<User> getAllFollowers(String alias) {
        return null;
    }
}
