package edu.byu.cs.tweeter.server.dao.factories;

import edu.byu.cs.tweeter.server.dao.daoInterfaces.iAuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iFeedDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iFollowDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iStatusDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iUserDAO;
import edu.byu.cs.tweeter.server.dao.dummy.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.dummy.FeedDAO;
import edu.byu.cs.tweeter.server.dao.dummy.FollowDAO;
import edu.byu.cs.tweeter.server.dao.dummy.StatusDAO;
import edu.byu.cs.tweeter.server.dao.dummy.UserDAO;

public class DummyFactory extends AbstractFactory{
    @Override
    public iUserDAO getUserDAO() {
        return new UserDAO();
    }

    @Override
    public iFollowDAO getFollowDAO() {
        return new FollowDAO();
    }

    @Override
    public iAuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }

    @Override
    public iStatusDAO getStatusDAO() {
        return new StatusDAO();
    }

    @Override
    public iFeedDAO getFeedDAO() {
        return new FeedDAO();
    }

    @Override
    public iImageDAO getS3DAO() {
        return null;
    }
}
