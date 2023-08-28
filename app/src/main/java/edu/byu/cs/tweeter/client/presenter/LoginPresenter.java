package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends AuthenticationPresenter implements UserService.loginObserver {

    public interface LoginView extends AuthenticationPresenter.AuthenticationView{}

    public LoginPresenter(AuthenticationView view){
        super(view);
    }

    @Override
    public void handleFailed(String message) {
        ((AuthenticationView)view).displayErrorMessage("Failed to login: " + message);
    }

    @Override
    public void handleException(Exception ex) {
        ((AuthenticationView)view).displayErrorMessage("Failed to login because of exception: " + ex.getMessage());
    }



    public void login(String alias, String password){
        ((AuthenticationView)view).clearErrorMessage();
        ((AuthenticationView)view).clearInfoMessage();

        String message = validateLogin(alias,password);
        if(message == null){
            ((AuthenticationView)view).clearErrorMessage();
            ((AuthenticationView)view).displayInfoMessage("Logging In...");
            new UserService().login(alias, password, this);
        }

        else{
            ((AuthenticationView)view).displayErrorMessage("Login Failed: "+ message);
        }

    }

    private String validateLogin(String alias, String password) {
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }

        return null;
    }

    @Override
    public void loginSucceed(User user, AuthToken auth) {

        ((AuthenticationView)view).navigateToUser(user);
        ((AuthenticationView)view).clearErrorMessage();
        ((AuthenticationView)view).displayInfoMessage("Hello "+ user.getName());
    }
}
