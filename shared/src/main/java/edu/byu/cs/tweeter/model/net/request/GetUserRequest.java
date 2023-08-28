package edu.byu.cs.tweeter.model.net.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

public class GetUserRequest {
    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }

    private AuthToken authToken;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    private String alias;
    public GetUserRequest(){}
    public GetUserRequest(AuthToken authToken, String alias)
    {
        this.alias = alias;
        this.authToken = authToken;
    }
}
