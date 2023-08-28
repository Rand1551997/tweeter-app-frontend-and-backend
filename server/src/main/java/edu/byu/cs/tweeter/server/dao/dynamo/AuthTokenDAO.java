package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iAuthTokenDAO;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Base64;

public class AuthTokenDAO implements iAuthTokenDAO {

    Table authTokenTable;

    public AuthTokenDAO(Table authtoken) {
        authTokenTable = authtoken;
    }

    public String generateNewToken() {
        byte[] randomBytes = new byte[24];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }

    @Override
    public AuthToken generateToken(String username) {
        String tokenNum= generateNewToken();
        String time = ""+((new Timestamp(System.currentTimeMillis())).getTime());
        AuthToken authToken = new AuthToken();
        authToken.setToken(tokenNum);
        authToken.setTime(time);
        Item tokenItem = new Item().withPrimaryKey("authtoken", authToken.getToken()).withString("username",username).withString("timestamp",authToken.getTime());
        authTokenTable.putItem(tokenItem);
        return authToken;
    }

    @Override
    public boolean deleteToken(String authToken) {
        System.out.println("Deleting Token: in ---> AuthTokenDAO");
        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey("authtoken", authToken);
        authTokenTable.deleteItem(deleteItemSpec);
        return true;
    }

    @Override
    public AuthToken getToken(String authToken) {
        Item item = authTokenTable.getItem("authtoken", authToken);
        AuthToken authToken1 =  new AuthToken();
        authToken1.setToken(item.get("authtoken").toString());
        authToken1.setTime(item.get("timestamp").toString());
        return authToken1;
    }


}
