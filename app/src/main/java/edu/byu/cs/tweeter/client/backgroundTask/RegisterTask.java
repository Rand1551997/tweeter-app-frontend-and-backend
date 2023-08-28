package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Handler;
import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

import java.io.IOException;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticateTask {
    private static final String LOG_TAG = "RegisterTask";
    private static final String URL_PATH = "/register";

    /**
     * The user's first name.
     */
    private String firstName;
    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private String image;


    public RegisterTask(String firstName, String lastName, String username, String password,
                        String image, Handler messageHandler) {
        super(username, password, messageHandler);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    @Override
    protected boolean runTask() {

        RegisterRequest request = new RegisterRequest(firstName,lastName,this.username,this.password,image);
        boolean success = false;
        try {
            RegisterResponse response = new ServerFacade().register(request,URL_PATH);
            this.user = response.getUser();
            this.authToken = response.getAuthToken();
            success = response.isSuccess();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }


        try {
            BackgroundTaskUtils.loadImage(this.user);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;


    }
}
