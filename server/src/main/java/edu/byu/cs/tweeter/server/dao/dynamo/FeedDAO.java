package edu.byu.cs.tweeter.server.dao.dynamo;

import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ResultPage;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.Utils;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iFeedDAO;
import edu.byu.cs.tweeter.server.dao.factories.BatchWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FeedDAO extends BatchWriter implements iFeedDAO{

    private static final String PARTITION_KEY = "alias";
    private static final String SORT_KEY = "timestamp";

    private Table feedTable;
    public FeedDAO(Table feed) {
        feedTable = feed;
    }

    public ResultPage getFeed(String username, Status lastStatus, Integer limit){

        ItemCollection<QueryOutcome> items = null;
        List<Status> statuses = null;
        try {
            QuerySpec spec = Utils.getBasicSpec(PARTITION_KEY, username, limit);

            if (lastStatus != null) {
                long timestamp = lastStatus.getTimeStamp();
                PrimaryKey lastItemKey = new PrimaryKey(PARTITION_KEY, username ,SORT_KEY, timestamp);
                spec.withExclusiveStartKey(lastItemKey);
            }

            items = feedTable.query(spec);
            Iterator<Item> iterator = items.iterator();

            Item item = null;
            statuses = new ArrayList<>();
            while (iterator.hasNext()) {
                item = iterator.next();
                User user = new User(item.getString("statusUserFirstName"), item.getString("statusUserLastName"), item.getString("statusUserAlias"), item.getString("statusUserImageUrl"));
                Status status = new Status(item.getString("post"), user, item.getString("datetime"), item.getList("urls"), item.getList("mentions"),item.getLong("timestamp"));
                statuses.add(status);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to get feeds");
        }

        return new ResultPage(Utils.checkHasMore(items), statuses);
    }

    @Override
    public boolean postFeed(List<String> followers, Status status) {
        User statusUser = status.getUser();
        long timestamp=System.currentTimeMillis();
        System.out.println("Entering posting Feed to all Users");

        TableWriteItems items = new TableWriteItems("feed");

        try {
            for (String follower : followers) {
                Item item = new Item()
                        .withPrimaryKey("alias", follower)
                        .withLong("timestamp", timestamp)
                        .withString("statusUserAlias", statusUser.getAlias())
                        .withString("statusUserFirstName", statusUser.getFirstName())
                        .withString("statusUserLastName", statusUser.getLastName())
                        .withString("statusUserImageUrl", statusUser.getImageUrl())
                        .withString("post", status.getPost())
                        .withString("datetime", status.getDatetime())
                        .withList("urls", status.getUrls())
                        .withList("mentions", status.getMentions());

                items.addItemToPut(item);

                if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                    loopBatchWrite(items);
                    items = new TableWriteItems("feed");
                }
            }

            if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
                loopBatchWrite(items);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

}
