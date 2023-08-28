package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;

import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.LogoutRequest;
import edu.byu.cs.tweeter.model.net.response.LogoutResponse;

import java.io.IOException;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends AuthenticatedTask{
    private static final String LOG_TAG = "LogoutTask";
    private static final String URL_PATH = "/logout";


    public LogoutTask(AuthToken authToken, Handler messageHandler) {
        super(authToken, messageHandler);
    }

    @Override
    protected boolean runTask() {

        LogoutRequest logoutRequest = new LogoutRequest(this.authToken);
        boolean s = false;
        try {
            LogoutResponse response = new ServerFacade().logout(logoutRequest,URL_PATH);
            s = response.isSucess();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }

        return s;
    }

}
