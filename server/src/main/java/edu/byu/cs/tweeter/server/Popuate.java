package edu.byu.cs.tweeter.server;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.dynamo.FollowDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class Popuate {


    public static void main(String[] args){

        final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion("us-west-2")
                .build();
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

        // How many follower users to add
        // We recommend you test this with a smaller number first, to make sure it works for you
        final int NUM_USERS = 10000;

        // The alias of the user to be followed by each user created
        // This example code does not add the target user, that user must be added separately.
        User targetUser = new User("@Randa","Rand","Al Rabadi","","https://tweeters3bucket.s3.us-west-2.amazonaws.com/%40Randa_profile_image");
        // Get instance of DAOs by way of the Abstract Factory Pattern

        UserDAO userDAO = new UserDAO(dynamoDB.getTable("users"));
        FollowDAO followDAO = new FollowDAO(dynamoDB.getTable("follows"));

        List<String> followers = new ArrayList<>();
        List<User> users = new ArrayList<>();

        // Iterate over the number of users you will create
        for (int i = 1; i <= NUM_USERS; i++) {

            String name = "Shireen " + i;
            String lastName = "Owais" + i;
            String alias = "@Shi" + i;

            User user = new User();
            user.setAlias(alias);
            user.setFirstName(name);
            user.setLastName(lastName);
            user.setImageUrl(FEMALE_IMAGE_URL);
            user.setPassword("1");
            users.add(user);

        }

        // Call the DAOs for the database logic
        if (users.size() > 0) {
            System.out.println("Writing to user Table");
            userDAO.addUserBatch(users);
            System.out.println("Finished Wrting to user Table");
        }
        if (users.size() > 0) {

            System.out.println("Writing to Follow Table");
            followDAO.addFollowersBatch(users, targetUser);
            System.out.println("Finished Wrting to Follow Table");
        }
    }
}
