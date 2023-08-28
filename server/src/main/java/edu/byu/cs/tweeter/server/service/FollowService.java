package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.net.ResultPage;
import edu.byu.cs.tweeter.model.net.request.*;
import edu.byu.cs.tweeter.model.net.response.*;
import edu.byu.cs.tweeter.server.dao.dynamo.FollowDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iFollowDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iUserDAO;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowService extends Service{

    //private AbstractFactory factory;

    public FollowService(AbstractFactory factory) {
        super (factory);
    }

    public iFollowDAO getFollowDAO() {
        return factory.getFollowDAO();
    }

    public iUserDAO getUserDAO(){ return factory.getUserDAO();}

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request) {
        ResultPage resultPage = getFollowDAO().getFollowing(request.getFollowerAlias(), request.getLastFolloweeAlias(), request.getLimit());
        return new FollowingResponse(resultPage.getItems(), resultPage.isHasMorePages());
    }



    public FollowResponse follow(FollowRequest request) {
        if(!validateToken(getAuthTokenDAO().getToken(request.getAuthToken()))){
            System.out.println("Not valid token");
            return new FollowResponse(false,"follow Failed");
        }

        boolean success = getFollowDAO().follow(getUserDAO().getUser(request.getUserLoggedIn()),getUserDAO().getUser(request.getFolloweeAlias()));
        if(success){
            return new FollowResponse(true,"Followed "+request.getFolloweeAlias()+" Successfully");
        }
        return new FollowResponse(false,"Follow Failed");
    }

    public FollowerCountResponse getFollowerCount(FollowerCountRequest r) {

        int num = getFollowDAO().getFollowerCount(r.getUserName());
        if(num==-1){
            return new FollowerCountResponse(num,false, "Got Follower Count Failed: Follow Service");
        }
        return new FollowerCountResponse(num,true, "Got Follower Count");
    }

    public FollowersResponse getFollowers(FollowersRequest request) {
        ResultPage resultPage = getFollowDAO().getFollowers(request.getFollowerAlias(), request.getLastFolloweeAlias(), request.getLimit());
        return new FollowersResponse(resultPage.getItems(), resultPage.isHasMorePages());
    }

    public UnfollowResponse unfollow(UnfollowRequest request) {

        if(!validateToken(request.getAuthToken())){
            System.out.println("Not valid token");
            return new UnfollowResponse(false,"Unfollow Failed");
        }
        boolean success = getFollowDAO().unfollow(request.getUserLoggedIn(), request.getUserBeingUnfollowed());
        if(success){
            return new UnfollowResponse(true,"Unfollowed "+request.getUserBeingUnfollowed()+" Successfully");
        }
        return new UnfollowResponse(false,"Unfollow Failed");
    }

    public FollowingCountResponse getFollowingCount(FollowingCountRequest r) {
        int num = getFollowDAO().getFollowingCount(r.getUserName());
        if(num==-1){
            return new FollowingCountResponse(num,false, "Got Following Count Failed: Follow Service");
        }
        return new FollowingCountResponse(num,true, "Got Following Count");
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) {
        if(!validateToken(getAuthTokenDAO().getToken(request.getToken().getToken()))){
            System.out.println("Not valid token");
            return new IsFollowerResponse(false,"Unfollow Failed");
        }
        boolean follows = getFollowDAO().isFollowing(request.getFollower(), request.getFollowee());
        if(follows){
            return new IsFollowerResponse(follows,"That users follow",true);
        }
        return new IsFollowerResponse(follows,"That users doesnt follow",true);
    }
}
