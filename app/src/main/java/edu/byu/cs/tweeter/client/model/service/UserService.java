package edu.byu.cs.tweeter.client.model.service;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class UserService extends ExecuteClass{

    public interface loginObserver extends ServiceObserver{
        void loginSucceed(User user, AuthToken auth);

    }

    public interface getUserObserver extends ServiceObserver{
        void getUserSucceed(User user);
    }

    public void getUser(String alias, AuthToken authToken,getUserObserver observer){
        execute(new GetUserTask(authToken, alias, new GetUserHandler(observer)));
    }

    public void login(String allias, String password, loginObserver observer){
        execute(new LoginTask(allias, password, new LoginHandler(observer)));
    }

    /**
     * Message handler (i.e., observer) for LoginTask
     */
    private class LoginHandler extends BackgroundTaskHandler {

        public LoginHandler(loginObserver observer){
            super(observer);
        }

        @Override
        protected void handleSuccess(Message msg) {
            User loggedInUser = (User) msg.getData().getSerializable(LoginTask.USER_KEY);
            AuthToken authToken = (AuthToken) msg.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);

            // Cache user session information
            Cache.getInstance().setCurrUser(loggedInUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);
            ((loginObserver)this.observer).loginSucceed(loggedInUser, authToken);
        }


    }

    private class GetUserHandler extends BackgroundTaskHandler {

        public GetUserHandler(getUserObserver observer) {super(observer);}

        @Override
        protected void handleSuccess(Message msg) {
            ((getUserObserver)this.observer).getUserSucceed((User) msg.getData().getSerializable(GetUserTask.USER_KEY));
        }
    }

    public interface getRegisterObserver extends ServiceObserver{

        void getRegisterSucceed(User user, AuthToken auth);
    }

    public void register(String firstName, String lastName,
                         String alias, String password, String imageBytesBase64, getRegisterObserver observer){
        execute(new RegisterTask(firstName, lastName,
                alias, password, imageBytesBase64, new RegisterHandler(observer)));
    }

    private class RegisterHandler extends BackgroundTaskHandler {

        public RegisterHandler( getRegisterObserver observer){
            super(observer);
        }



        @Override
        protected void handleSuccess(Message msg) {
            User registeredUser = (User) msg.getData().getSerializable(RegisterTask.USER_KEY);
            AuthToken authToken = (AuthToken) msg.getData().getSerializable(RegisterTask.AUTH_TOKEN_KEY);

            Cache.getInstance().setCurrUser(registeredUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);
            ((getRegisterObserver)observer).getRegisterSucceed(registeredUser, authToken);
        }

    }



    public interface logoutObserver extends ServiceObserver{
        void logoutSuccess();
    }

    public void logout(AuthToken authToken, logoutObserver observer){
        execute(new LogoutTask(authToken, new LogoutHandler(observer)));
    }

    private class LogoutHandler extends BackgroundTaskHandler {

        public LogoutHandler(logoutObserver observer){
            super(observer);
        }

        @Override
        protected void handleSuccess(Message msg) {
            ((logoutObserver)this.observer).logoutSuccess();
        }
    }
}

