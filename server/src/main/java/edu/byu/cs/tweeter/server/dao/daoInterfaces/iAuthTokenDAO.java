package edu.byu.cs.tweeter.server.dao.daoInterfaces;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public interface iAuthTokenDAO {

    public AuthToken generateToken(String alias);
    public boolean deleteToken(String alias);
    public AuthToken getToken(String authToken);
}
