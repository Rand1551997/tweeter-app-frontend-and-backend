package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;
import edu.byu.cs.tweeter.server.dao.factories.DBFactory;
import edu.byu.cs.tweeter.server.service.UserService;

import java.text.ParseException;

public class GetUserHandler implements RequestHandler<GetUserRequest, GetUserResponse> {

    @Override
    public GetUserResponse handleRequest(GetUserRequest input, Context context) {
        AbstractFactory factory =  HandleConfig.getInstance();
        UserService userService = new UserService(factory);
        try {
            return userService.getUser(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new GetUserResponse(null, false, "Error in GetUserHandler");
    }
}
