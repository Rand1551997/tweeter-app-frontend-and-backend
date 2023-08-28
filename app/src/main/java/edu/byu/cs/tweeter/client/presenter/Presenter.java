package edu.byu.cs.tweeter.client.presenter;

public class Presenter {
    protected View view;

    public Presenter(View view){
        this.view = view;
    }

    public interface View{
        void displayInfoMessage(String message);
        void displayErrorMessage(String message);
    }

}
