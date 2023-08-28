package edu.byu.cs.tweeter.server.service;


import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ResultPage;
import edu.byu.cs.tweeter.model.net.request.BatchStatusRequest;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iAuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iFeedDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iFollowDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iStatusDAO;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;

import java.util.ArrayList;
import java.util.List;

public class StatusService extends Service{

    public StatusService(AbstractFactory factory){
        super (factory);
    }

    public iStatusDAO getStatusDAO(){
        return factory.getStatusDAO();
    }

    public iAuthTokenDAO getAuthTokenDAO() {return factory.getAuthTokenDAO();}

    public iFollowDAO getFollowDAO() {return factory.getFollowDAO();}

    public iFeedDAO getFeedDAO() { return factory.getFeedDAO();}


    public GetFeedResponse getFeed(GetFeedRequest request){
        if(!validateToken(getAuthTokenDAO().getToken(request.getAuthToken().getToken()))){
            return new GetFeedResponse(false, false);
        }
        ResultPage result = getFeedDAO().getFeed(request.getUserName(),request.getLastStatus(), request.getLimit());
        GetFeedResponse response = new GetFeedResponse();
        response.setFeedList(result.getItems());
        response.setHasMorePages(result.isHasMorePages());
        response.setSuccess(true);
        response.setMessage("Gotten Successfully the feed");
        return response;
    }


    public PostStatusResponse postStatus(PostStatusRequest request) {
        if(!validateToken(getAuthTokenDAO().getToken(request.getAuthToken().getToken()))){
            return new PostStatusResponse(false, "Failed: authentication expired");
        }

        if(getStatusDAO().postStory(request.getStatusStory(), request.getCurrUser())){
            return new PostStatusResponse(true, "Posted Successfully");
        }

        return new PostStatusResponse(false, "Posting Failed");
    }

    public GetStoryResponse getStory(GetStoryRequest request) {
        if(!validateToken(getAuthTokenDAO().getToken(request.getAuthToken().getToken()))){
            return new GetStoryResponse(false,false);
        }
        Status date = null;
        if(request.getLastStatus() != null){
            date = request.getLastStatus();
        }
        ResultPage result = getStatusDAO().getStory(request.getUserName(),date, request.getLimit());
        if(result != null){
            return new GetStoryResponse(result.isHasMorePages(), result.getItems(), true, "Got stories");
        }

        return new GetStoryResponse(false,false);
    }

    public List<BatchStatusRequest> prepareBatches(BatchStatusRequest batchStatusRequest) {

        List<BatchStatusRequest> result = new ArrayList<>();
        int BATCH_SIZE = 100;
        List<User> gotAllFollowers = getFollowDAO().getAllFollowers(batchStatusRequest.getUserWhoPosted());
        List<String> followers = new ArrayList<>();
        for(User user: gotAllFollowers){
            followers.add(user.getAlias());
        }
        List<String> temp = new ArrayList<>();
        for(int i = 0; i < followers.size(); i++){
            temp.add(followers.get(i));
            if(temp.size()==BATCH_SIZE){
                BatchStatusRequest batchRequest = new BatchStatusRequest();
                batchRequest.setUserWhoPosted(batchStatusRequest.getUserWhoPosted());
                batchRequest.setUsers(temp);
                result.add(batchRequest);
                temp = new ArrayList<>();
            }

            if(i == followers.size()-1){
                BatchStatusRequest batchRequest = new BatchStatusRequest();
                batchRequest.setUserWhoPosted(batchStatusRequest.getUserWhoPosted());
                batchRequest.setUsers(temp);
                result.add(batchRequest);
                temp = new ArrayList<>();
            }

        }

        return result;
    }
    
    //TODO: COME BACK HERE FOR THE NULL

    public boolean batchPostStatus(BatchStatusRequest request) {
        return getFeedDAO().postFeed(request.getUsers(), (Status)getStatusDAO().getStory(request.getUserWhoPosted(),null,1).getItems().get(0));
    }
}
