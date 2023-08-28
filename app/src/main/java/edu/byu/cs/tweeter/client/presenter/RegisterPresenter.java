package edu.byu.cs.tweeter.client.presenter;

import android.widget.ImageView;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends AuthenticationPresenter implements UserService.getRegisterObserver {


    @Override
    public void getRegisterSucceed(User user, AuthToken auth) {
        ((RegisterView)this.view).navigateToUser(user);
        ((RegisterView)this.view).displayInfoMessage("Hello "+ user.getName());
    }

    public RegisterPresenter(RegisterView view){
        super(view);
    }

    @Override
    public void handleFailed(String message) {
        ((RegisterView)this.view).displayErrorMessage("Failed to login: " + message);

    }

    @Override
    public void handleException(Exception ex) {
        view.displayErrorMessage("Failed to login because of exception: " + ex.getMessage());
    }

    public interface RegisterView extends AuthenticationView{
    }

    public void register(String firstName, String lastName, String alias, String password,
                         String image, ImageView imageToUpload){
        ((RegisterView)this.view).clearErrorMessage();

        String message = validateRegistration(firstName, lastName, alias, password, image, imageToUpload);
        if(message == null){
            ((RegisterView)this.view).displayInfoMessage("Registering...");
            new UserService().register(firstName, lastName, alias, password, image, this);
        }

        else{
            ((RegisterView)this.view).displayErrorMessage("Register Failed: " + message);
        }

    }

    public String validateRegistration(String firstName, String lastName, String alias, String password,
                                       String image, ImageView imageToUpload) {
        if (firstName.length() == 0) {
            return "First Name cannot be empty.";
        }
        if (lastName.length() == 0) {
            return "Last Name cannot be empty.";
        }
        if (alias.length() == 0) {
            return "Alias cannot be empty.";
        }
        if (alias.charAt(0) != '@') {
            return "Alias must begin with @.";
        }
        if (alias.length() < 2) {
            return "Alias must contain 1 or more characters after the @.";
        }
        if (password.length() == 0) {
            return "Password cannot be empty.";
        }

        if (imageToUpload.getDrawable() == null) {
            return "Profile image must be uploaded.";
        }

        else{
            return null;
        }
    }
}
