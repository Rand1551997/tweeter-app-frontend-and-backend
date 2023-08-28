package edu.byu.cs.tweeter.server.dao.dummy;

import com.amazonaws.services.dynamodbv2.document.Table;

import java.text.ParseException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iUserDAO;
import edu.byu.cs.tweeter.server.util.FakeData;

public class UserDAO implements iUserDAO {

    @Override
    public User login(String username, String password) throws RuntimeException {
        return new FakeData().getFirstUser();
    }

    @Override
    public User getUser(String username) throws RuntimeException {
        return new FakeData().findUserByAlias(username);
    }

    @Override
    public Table getUserTable() {
        return null;
    }

    @Override
    public boolean logout() throws ParseException {
        return true;
    }

    @Override
    public User register(String username, String password, String firstName, String lastName, String url) throws ParseException {
        return new FakeData().getFirstUser();
    }
}
