package edu.byu.cs.tweeter.server.dao.dynamo;

import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.*;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ResultPage;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iFollowDAO;
import edu.byu.cs.tweeter.server.dao.factories.BatchWriter;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class FollowDAO extends BatchWriter implements iFollowDAO {

    private static final String FOLLOWS_TABLE_PRIMARY_KEY = "follower_handle";
    private static final String FOLLOWS_TABLE_SORT_KEY = "followee_handle";
    private static final String FOLLOWS_TABLE_FOLLOWER_NAME = "follower_name";
    private static final String FOLLOWS_TABLE_FOLLOWEE_NAME = "followee_name";
    private static final String FOLLOWS_TABLE_NAME = "follows";
    private static final String FOLLOWS_INDEX_NAME = "follows_index";
    private static final String FOLLOWEE_LAST_NAME = "FolloweeLastName";
    private static final String FOLLOWER_LAST_NAME = "FollowerLastName";

    private Table followTable;

    public FollowDAO(Table follow) {
        followTable = follow;
    }

    @Override
    public int getFollowerCount(String alias) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#followee", FOLLOWS_TABLE_SORT_KEY);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":handle", new AttributeValue(alias));

        System.out.println("index FollowerCount");

        ScanRequest request = new ScanRequest().withTableName(FOLLOWS_TABLE_NAME).withFilterExpression("#followee = :handle")
                .withIndexName(FOLLOWS_INDEX_NAME)
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues);
        ScanResult scanResult = amazonDynamoDB.scan(request);
        return scanResult.getCount();
    }

    @Override
    public int getFollowingCount(String alias) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#follower", FOLLOWS_TABLE_PRIMARY_KEY);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":handle", new AttributeValue(alias));

        System.out.print("Following Count");
        ScanRequest request = new ScanRequest().withTableName(FOLLOWS_TABLE_NAME).withFilterExpression("#follower = :handle")
                .withExpressionAttributeNames(attrNames).withExpressionAttributeValues(attrValues);
        ScanResult scanResult = amazonDynamoDB.scan(request);
        return scanResult.getCount();
    }

    @Override
    public ResultPage getFollowers(String userAlias, String lastFolloweeAlias,int limit) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#followee", FOLLOWS_TABLE_SORT_KEY);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":followee_handle", new AttributeValue().withS(userAlias));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(FOLLOWS_TABLE_NAME).withIndexName(FOLLOWS_INDEX_NAME)
                .withKeyConditionExpression("#followee = :followee_handle")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues);

        if(limit > -1){
            queryRequest.withLimit(limit);
        }

        if (lastFolloweeAlias != null) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(FOLLOWS_TABLE_SORT_KEY, new AttributeValue().withS(userAlias));
            startKey.put(FOLLOWS_TABLE_PRIMARY_KEY, new AttributeValue().withS(lastFolloweeAlias));
            System.out.println("inside Start Key");
            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        List<User> followees = new ArrayList<>();
        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();

        if (items != null) {
            for (Map<String, AttributeValue> item: items) {
                String alias = item.get("follower_handle").getS();
                String first_name = item.get(FOLLOWS_TABLE_FOLLOWER_NAME).getS();
                String last_name = item.get(FOLLOWER_LAST_NAME).getS();
                String image_url = item.get("follower_image_url").getS();
                followees.add(new User(first_name, last_name, alias, image_url));
            }
        }
        System.out.println("size of following is " + followees.size());
        boolean hasMore = false;
        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            hasMore = true;
        }
        //String last = lastKey.get("follower_handle").getS() == null ? "No more Users" : lastKey.get("follower_handle").getS();
        ResultPage result = new ResultPage(hasMore, followees);
        return result;
    }

    @Override
    public ResultPage getFollowing(String userAlias, String lastFolloweeAlias,int limit) {
        Map<String, String> attrNames = new HashMap<>();
        attrNames.put("#follower", FOLLOWS_TABLE_PRIMARY_KEY);

        Map<String, AttributeValue> attrValues = new HashMap<>();
        attrValues.put(":follower_handle", new AttributeValue().withS(userAlias));

        QueryRequest queryRequest = new QueryRequest()
                .withTableName(FOLLOWS_TABLE_NAME)
                .withKeyConditionExpression("#follower = :follower_handle")
                .withExpressionAttributeNames(attrNames)
                .withExpressionAttributeValues(attrValues);

        if(limit > -1){
            queryRequest.withLimit(limit);
        }

        if (lastFolloweeAlias != null) {
            Map<String, AttributeValue> startKey = new HashMap<>();
            startKey.put(FOLLOWS_TABLE_PRIMARY_KEY, new AttributeValue().withS(userAlias));
            startKey.put(FOLLOWS_TABLE_SORT_KEY, new AttributeValue().withS(lastFolloweeAlias));
            System.out.println("inside Start Key");
            queryRequest = queryRequest.withExclusiveStartKey(startKey);
        }

        List<User> followees = new ArrayList<>();
        QueryResult queryResult = amazonDynamoDB.query(queryRequest);
        List<Map<String, AttributeValue>> items = queryResult.getItems();

        if (items != null) {
            for (Map<String, AttributeValue> item: items) {
                String alias = item.get("followee_handle").getS();
                String first_name = item.get(FOLLOWS_TABLE_FOLLOWEE_NAME).getS();
                String last_name = item.get(FOLLOWEE_LAST_NAME).getS();
                String image_url = item.get("followee_image_url").getS();
                followees.add(new User(first_name, last_name, alias, image_url));
            }
        }
        System.out.println("size of following is " + followees.size());
        boolean hasMore = false;
        Map<String, AttributeValue> lastKey = queryResult.getLastEvaluatedKey();
        if (lastKey != null) {
            hasMore = true;
        }
        ResultPage result = new ResultPage(hasMore, followees);
        return result;
    }

    @Override
    public boolean isFollowing(String loggedUser, String targetUser) {
        boolean follows = false;
        Item item = followTable.getItem(FOLLOWS_TABLE_PRIMARY_KEY,loggedUser, FOLLOWS_TABLE_SORT_KEY, targetUser);
        if (item != null) {
            follows = true;
        }
        return follows;
    }

    @Override
    public boolean follow(User loggedInUser, User followUser) {
        Item item = new Item()
                .withPrimaryKey(FOLLOWS_TABLE_PRIMARY_KEY, loggedInUser.getAlias(), FOLLOWS_TABLE_SORT_KEY, followUser.getAlias())
                .withString(FOLLOWS_TABLE_FOLLOWER_NAME, loggedInUser.getFirstName())
                .withString(FOLLOWS_TABLE_FOLLOWEE_NAME, followUser.getFirstName()).withString(FOLLOWER_LAST_NAME, followUser.getFirstName())
                .withString(FOLLOWEE_LAST_NAME, followUser.getLastName()).withString("followee_image_url", followUser.getImageUrl()).withString("follower_image_url", loggedInUser.getImageUrl());
        PutItemOutcome res =  followTable.putItem(item);
        if(res == null){return false;}
        return true;
    }

    @Override
    public boolean unfollow(String loggedInUser, String userBeingUnfollowed) {
        System.out.println("Logged User: "+loggedInUser+" userbeingFolowed: "+ userBeingUnfollowed);
        DeleteItemOutcome outcome = followTable.deleteItem(FOLLOWS_TABLE_PRIMARY_KEY, loggedInUser, FOLLOWS_TABLE_SORT_KEY, userBeingUnfollowed);
        if(outcome == null){return false;}
        return true;
    }

    @Override
    public List<User> getAllFollowers(String alias) {

        ResultPage result = getFollowers(alias, null, 250);
        List<User> users = result.getItems();
        while(result.isHasMorePages()){
            result = getFollowers(alias, users.get(users.size()-1).getAlias(), 250);
            users.addAll(result.getItems());
        }
        System.out.println("Followers size: "+users.size());
        return users;
    }


    public void addFollowersBatch(List<User> followers, User follow_target) {

        // Constructor for TableWriteItems takes the name of the table, which I have stored in TABLE_USER
        TableWriteItems items = new TableWriteItems("follows");

        // Add each user into the TableWriteItems object
        for (User user : followers) {
            Item item = new Item()
                    .withPrimaryKey(FOLLOWS_TABLE_PRIMARY_KEY, user.getAlias(), FOLLOWS_TABLE_SORT_KEY, follow_target.getAlias())
                    .withString(FOLLOWS_TABLE_FOLLOWER_NAME, user.getFirstName())
                    .withString(FOLLOWS_TABLE_FOLLOWEE_NAME, follow_target.getFirstName())
                    .withString(FOLLOWER_LAST_NAME, user.getFirstName())
                    .withString(FOLLOWEE_LAST_NAME, follow_target.getLastName())
                    .withString("followee_image_url", follow_target.getImageUrl())
                    .withString("follower_image_url", user.getImageUrl());
            items.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems("follows");
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }

    }
    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);



}
