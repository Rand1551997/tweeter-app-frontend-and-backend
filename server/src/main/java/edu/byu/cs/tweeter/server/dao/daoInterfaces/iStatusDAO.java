package edu.byu.cs.tweeter.server.dao.daoInterfaces;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ResultPage;

public interface iStatusDAO {

    public boolean postStory(Status status, User user);
    public ResultPage getStory(String username, Status lastStatus, int limit);
}
