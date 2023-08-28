package edu.byu.cs.tweeter.server.dao.daoInterfaces;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ResultPage;

import java.util.List;

public interface iFeedDAO {
    public boolean postFeed(List<String> followers, Status status);
    public ResultPage getFeed(String username, Status lastStatus, Integer limit);
}
