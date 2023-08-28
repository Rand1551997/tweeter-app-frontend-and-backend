package edu.byu.cs.tweeter.server.dao.factories;

import edu.byu.cs.tweeter.server.dao.daoInterfaces.*;

public abstract class AbstractFactory {

    public abstract iUserDAO getUserDAO();
    public abstract iFollowDAO getFollowDAO();
    public abstract iAuthTokenDAO getAuthTokenDAO();
    public abstract iStatusDAO getStatusDAO();
    public abstract iFeedDAO getFeedDAO();
    public abstract iImageDAO getS3DAO();

}
