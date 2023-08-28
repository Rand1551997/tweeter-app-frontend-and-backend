package edu.byu.cs.tweeter.server.dao.daoInterfaces;

import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

import java.text.ParseException;

public interface iUserDAO {

    public User login(String username, String password) throws RuntimeException;
    public User getUser(String username) throws RuntimeException;
    public Table getUserTable();
    public boolean logout() throws ParseException;
    public User register(String username, String password, String firstName, String lastName, String url) throws ParseException;
}
