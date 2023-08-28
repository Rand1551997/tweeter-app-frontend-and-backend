package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.s3.model.S3DataSource;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ResultPage;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.PagedResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.Utils;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iStatusDAO;
import edu.byu.cs.tweeter.server.util.FakeData;
import edu.byu.cs.tweeter.server.util.Pair;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StatusDAO implements iStatusDAO {

    private Table storyTable;

    public StatusDAO(Table story) {
        storyTable = story;
    }

    public ResultPage getStory(String username, Status lastStatusDate, int limit){
        ItemCollection<QueryOutcome> items = null;
        List<Status> statuses = null;
        try {
            QuerySpec spec = Utils.getBasicSpec("alias", username, limit);

            if (lastStatusDate != null) {
                PrimaryKey lastItemKey = new PrimaryKey("alias", username, "timestamp", ""+lastStatusDate.getTimeStamp());
                spec.withExclusiveStartKey(lastItemKey);
            }

            items = storyTable.query(spec);
            Iterator<Item> iterator = items.iterator();

            Item item = null;
            statuses = new ArrayList<>();
            while (iterator.hasNext()) {
                item = iterator.next();
                User user = new User(item.getString("firstName"), item.getString("lastName"), item.getString("alias"), item.getString("imageURL"));
                Status status = new Status(item.getString("post"), user, item.getString("datetime"), item.getList("urls"), item.getList("mentions"),Long.parseLong(item.getString("timestamp")));
                statuses.add(status);
            }
        } catch (Exception  e) {
            throw new RuntimeException(e);
        }
        return new ResultPage(Utils.checkHasMore(items), statuses);
    }
    @Override
    public boolean postStory(Status status, User currUser) {

        User user = currUser;
        long millis=System.currentTimeMillis();
        java.util.Date date=new java.util.Date(millis);

        Item item = new Item()
                .withPrimaryKey("alias", user.getAlias())
                .withString("timestamp", ""+millis)
                .withString("firstName", user.getFirstName())
                .withString("lastName", user.getLastName())
                .withString("imageURL", user.getImageUrl())
                .withString("datetime", date.toString())
                .withString("post", status.getPost())
                .withList("urls", status.getUrls())
                .withList("mentions", status.getMentions());

        storyTable.putItem(item);
        return true;
    }















    private static AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);

}
