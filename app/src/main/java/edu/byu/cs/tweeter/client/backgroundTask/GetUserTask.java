package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;

import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;

import java.io.IOException;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AuthenticatedTask {
    private static final String LOG_TAG = "GetUserTask";
    private static final String URL_PATH = "/getuser";
    public static final String USER_KEY = "user";


    /**
     * Alias (or handle) for user whose profile is being retrieved.
     */
    private String alias;

    private User user;


    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler) {
        super(authToken, messageHandler);
        this.alias = alias;
    }

    @Override
    protected boolean runTask() {

        GetUserRequest getUserRequest = new GetUserRequest(this.authToken, alias);
        try {
            GetUserResponse response = new ServerFacade().getUser(getUserRequest,URL_PATH);

            user = response.getUser();
            if(user == null){
                return false;
            }

            BackgroundTaskUtils.loadImage(user);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }


        return false;
    }

    @Override
    protected void loadSuccessBundle(Bundle msgBundle) {
        msgBundle.putSerializable(USER_KEY, this.user);
    }

}