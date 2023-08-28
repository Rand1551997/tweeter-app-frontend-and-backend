package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.FollowerCountRequest;
import edu.byu.cs.tweeter.model.net.response.FollowerCountResponse;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;
import edu.byu.cs.tweeter.server.dao.factories.DBFactory;
import edu.byu.cs.tweeter.server.service.FollowService;

public class GetFollowersCountHandler implements RequestHandler<FollowerCountRequest, FollowerCountResponse> {
    @Override
    public FollowerCountResponse handleRequest(FollowerCountRequest r, Context context) {
        AbstractFactory factory =  HandleConfig.getInstance();
        FollowService followingService = new FollowService(factory);
        return followingService.getFollowerCount(r);
    }
}
