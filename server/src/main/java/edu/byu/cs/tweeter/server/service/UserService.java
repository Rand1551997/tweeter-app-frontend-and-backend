package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.dao.dynamo.S3DAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iAuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iUserDAO;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;


import java.text.ParseException;

public class UserService extends Service{

    //private AbstractFactory factory;

    public UserService(AbstractFactory factory){
        super (factory);
    }

    public iUserDAO getUserDAO(){ return factory.getUserDAO();}

    public iAuthTokenDAO getAuthTokenDAO() { return factory.getAuthTokenDAO();}


    public LoginResponse login(LoginRequest request){

        User user = null;
        try {
            user = getUserDAO().login(request.getUsername(), request.getPassword());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return new LoginResponse(null, null, false, e.getMessage());
        }
        AuthToken authToken = getAuthTokenDAO().generateToken(request.getUsername());
        return new LoginResponse(user, authToken);
    }

    public RegisterResponse register(RegisterRequest registerRequest) throws ParseException {
        if(registerRequest.getAlias()== null || registerRequest.getPassword() == null || registerRequest.getFirstName() == null ||
            registerRequest.getLastName() == null || registerRequest.getImageURL() == null){
            throw new RuntimeException("One of the credentials is null");
        }
        //String uri = .getImageURL();
        String uri = new S3DAO().upload(registerRequest.getAlias(), registerRequest.getImageURL());
        User user = getUserDAO().register(registerRequest.getAlias(), registerRequest.getPassword(), registerRequest.getFirstName(), registerRequest.getLastName(), uri);
        AuthToken authToken = getAuthTokenDAO().generateToken(registerRequest.getAlias());
        return new RegisterResponse(user, authToken, true, "Successfully registered "+registerRequest.getFirstName());
    }



    public GetUserResponse getUser(GetUserRequest request) throws ParseException {
        User user = getUserDAO().getUser(request.getAlias());
        AuthToken authToken = getAuthTokenDAO().getToken(request.getAuthToken().getToken());
        if(validateToken(authToken)){
            return  new GetUserResponse(user, true, "Successfully gotten "+ request.getAlias());
        }
        return new GetUserResponse(null, false, "Failed in UserService");

    }

    public LogoutResponse logout(LogoutRequest request)
    {
        boolean outcome = getAuthTokenDAO().deleteToken(request.getToken().getToken());
        System.out.println("Deleted Token");
        if(outcome){
            return new LogoutResponse(true,"Logging out");
        }
        return new LogoutResponse(false,"Failed in UserService");
    }
}
