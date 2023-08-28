package edu.byu.cs.tweeter.server.dao.factories;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

import edu.byu.cs.tweeter.server.dao.daoInterfaces.*;
import edu.byu.cs.tweeter.server.dao.dynamo.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.FeedDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.FollowDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.StatusDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.UserDAO;

public class DBFactory extends AbstractFactory{

    private iUserDAO userDAO;
    private iAuthTokenDAO authTokenDAO;
    private iFeedDAO feedDAO;
    private iStatusDAO statusDAO;
    private iFollowDAO followDAO;

    private DynamoDB dynamoDB;

    public DynamoDB getDbFactory(){

        if(dynamoDB == null){
            AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
                    .standard()
                    .withRegion("us-west-2")
                    .build();

            dynamoDB = new DynamoDB(amazonDynamoDB);
        }


        return dynamoDB;
    }

    @Override
    public iUserDAO getUserDAO() {
        if(userDAO == null){
            System.out.println("instantiating getUserDAO and gettingaDBFactory table for users");
            userDAO = new UserDAO(getDbFactory().getTable("users"));
        }

        return userDAO;
    }

    @Override
    public iFollowDAO getFollowDAO() {
        if(followDAO == null){
            followDAO = new FollowDAO(getDbFactory().getTable("follows"));
        }

        return followDAO;
    }

    @Override
    public iAuthTokenDAO getAuthTokenDAO() {
        if(authTokenDAO == null){
            authTokenDAO = new AuthTokenDAO(getDbFactory().getTable("authtoken"));
        }
        return authTokenDAO;
    }

    @Override
    public iStatusDAO getStatusDAO() {
        if(statusDAO == null){
            statusDAO = new StatusDAO(getDbFactory().getTable("storyTable"));
        }
        return statusDAO;
    }

    @Override
    public iFeedDAO getFeedDAO() {
        if(feedDAO == null){
            feedDAO = new FeedDAO(getDbFactory().getTable("feed"));
        }
        return feedDAO;
    }

    @Override
    public iImageDAO getS3DAO() {
        return null;
    }
}
