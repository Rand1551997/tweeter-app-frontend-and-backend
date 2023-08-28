package edu.byu.cs.tweeter.server.dao.dummy;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ResultPage;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iStatusDAO;
import edu.byu.cs.tweeter.server.util.FakeData;
import edu.byu.cs.tweeter.server.util.Pair;

public class StatusDAO implements iStatusDAO {
    @Override
    public boolean postStory(Status status, User user) {
        return true;
    }

    @Override
    public ResultPage getStory(String username, Status lastStatus, int limit) {
        Pair<List<Status>, Boolean> pageOfStatus = new FakeData().getPageOfStatus(lastStatus, limit);
        return new ResultPage(pageOfStatus.getSecond(), pageOfStatus.getFirst());
    }
}
