package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;
import edu.byu.cs.tweeter.server.dao.factories.DBFactory;
import edu.byu.cs.tweeter.server.service.UserService;

import java.text.ParseException;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful login.
 */
public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse handleRequest(LoginRequest loginRequest, Context context) {
        AbstractFactory factory =  HandleConfig.getInstance();
        System.out.println(loginRequest.getUsername()+" "+loginRequest.getPassword());
        UserService userService = new UserService(factory);
        if(factory.getUserDAO().getUserTable() == null) {
            System.out.println("Dynamo User table is still null: thatw in the handler: Table wasn't gotten succesfully");
        }
        return userService.login(loginRequest);
    }
}
