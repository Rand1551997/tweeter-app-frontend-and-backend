package edu.byu.cs.tweeter.server.dao.dummy;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iAuthTokenDAO;
import edu.byu.cs.tweeter.server.util.FakeData;

public class AuthTokenDAO implements iAuthTokenDAO {
    @Override
    public AuthToken generateToken(String alias) {
        return new FakeData().getAuthToken();
    }

    @Override
    public boolean deleteToken(String alias) {
        return true;
    }

    @Override
    public AuthToken getToken(String authToken) {
        return new FakeData().getAuthToken();
    }
}
