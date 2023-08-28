package edu.byu.cs.tweeter.server.dao.dynamo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.PasswordHasher;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iUserDAO;
import edu.byu.cs.tweeter.server.util.FakeData;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class UserDAO extends PasswordHasher implements iUserDAO{

    private Table userTable;

    public UserDAO(Table users) {
        userTable = users;
    }

    public Table getUserTable() {
        return userTable;
    }

    @Override
    public User login(String username, String password) throws RuntimeException{
        Item userItem = userTable.getItem("username",username);
        if(userItem == null){
            throw new RuntimeException("Invalid Credentials");
        }
        User user = getUser(userItem.get("username").toString());
        if(user == null || !authenticate(password, user.getPassword())){
            throw new RuntimeException("Incorrect password");
        }
        return user;
    }

    @Override
    public User getUser(String username) throws RuntimeException {
        GetItemSpec spec = new GetItemSpec().withPrimaryKey("username", username);
        Item userItem = null;
        if(userTable == null){
            System.out.println("UserTable is null");
        }
        try {
            System.out.print("Trying to get Item from Table");
            userItem = userTable.getItem(spec);
            if(userItem == null){
                System.out.println("getting Item failed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        if (userItem == null) {
            throw new RuntimeException("User does not exist");
        }
        System.out.println("");
        User user = new User(userItem.get("username").toString(),
                userItem.get("firstname").toString(),userItem.get("lastname").toString(),
                userItem.get("password").toString(),userItem.get("imageurl").toString());
        return user;
    }

    @Override
    public boolean logout() throws ParseException {

        return true;
    }

    @Override
    public User register(String username, String password, String firstName, String lastName, String image) throws ParseException{

        System.out.println("Image");

        System.out.println("Finished s3.upload");
        String hashedPass = hash(password);
        Item item = new Item().withPrimaryKey("username", username).withString("firstname", firstName)
                .withString("lastname", lastName).withString("password", hashedPass).withString("imageurl", image);

        userTable.putItem(item);
        return new User(username,firstName,lastName,hashedPass,image);
    }


    public void addUserBatch(List<User> users) {

        // Constructor for TableWriteItems takes the name of the table, which I have stored in TABLE_USER
        TableWriteItems items = new TableWriteItems("users");

        // Add each user into the TableWriteItems object
        for (User user : users) {
            Item item = new Item().withPrimaryKey("username", user.getAlias()).withString("firstname", user.getFirstName())
                    .withString("lastname", user.getLastName()).withString("password", hash(user.getPassword())).withString("imageurl", user.getImageUrl());

            items.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems("users");
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }
    }


}
