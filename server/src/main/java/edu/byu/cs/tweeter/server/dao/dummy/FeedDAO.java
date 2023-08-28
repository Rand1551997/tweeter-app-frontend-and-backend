package edu.byu.cs.tweeter.server.dao.dummy;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.ResultPage;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iFeedDAO;
import edu.byu.cs.tweeter.server.util.FakeData;
import edu.byu.cs.tweeter.server.util.Pair;


public class FeedDAO implements iFeedDAO {
    @Override
    public boolean postFeed(List<String> followers, Status status) {
        return false;
    }

    @Override
    public ResultPage getFeed(String username, Status lastStatus, Integer limit) {
        Pair<List<Status>, Boolean> pageOfStatus = new FakeData().getPageOfStatus(lastStatus, limit);
        return new ResultPage(pageOfStatus.getSecond(), pageOfStatus.getFirst());
    }
}
