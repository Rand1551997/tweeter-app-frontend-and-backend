package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iAuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iUserDAO;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;
import edu.byu.cs.tweeter.server.dao.factories.DBFactory;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

public class Service {
    //private DBFactory factory = new DBFactory();

    protected AbstractFactory factory;

    public Service(AbstractFactory factory){
        this.factory = factory;
    }
    public iAuthTokenDAO getAuthTokenDAO() { return factory.getAuthTokenDAO();}

    public boolean validateToken(AuthToken authToken){
        if(authToken == null){
            return true;
        }
        AuthToken tokenFromDB = factory.getAuthTokenDAO().getToken(authToken.getToken());

        long currentTime = Instant.now().toEpochMilli();
        long time = 10*60*1000;
        long timestamp = Long.parseLong(authToken.getTime());

        return (tokenFromDB!=null) && (currentTime < (time + timestamp));
        //return true;
    }



}
