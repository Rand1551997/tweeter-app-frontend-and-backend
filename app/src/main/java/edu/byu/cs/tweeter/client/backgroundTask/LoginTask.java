package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.*;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

import java.io.IOException;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticateTask {

    private static final String LOG_TAG = "LoginTask";
    private static final String URL_PATH = "/login";

    public LoginTask(String username, String password, Handler messageHandler) {
        super(username, password, messageHandler);
    }

    private void warmUp(){
        try {
            new ServerFacade().getFeed(new GetFeedRequest(), "/getfeed");
        }catch(RuntimeException | IOException | TweeterRemoteException e){
            e.printStackTrace();
        }

        try {
            new ServerFacade().getStory(new GetStoryRequest(), "getstory");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }

        try {
            new ServerFacade().getFollowers(new FollowersRequest(), "getfollowers");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }
        try {
            new ServerFacade().getFollowees(new FollowingRequest(), "/getfollowees");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }

        try {
            new ServerFacade().getFollowersCount(new FollowerCountRequest(), "/followerscount");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }

        try {
            new ServerFacade().postStatus(new PostStatusRequest(), "/poststatus");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }

        try {
            new ServerFacade().getFollowingCount(new FollowingCountRequest(), "/followeescount");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }
        try {
            new ServerFacade().getUser(new GetUserRequest(), "/getuser");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }
        try {
            new ServerFacade().logout(new LogoutRequest(), "/logout");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }
        try {
            new ServerFacade().follow(new FollowRequest(), "/follow");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }
        try {
            new ServerFacade().unfollow(new UnfollowRequest(), "/unfollow");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }
        try {
            new ServerFacade().isFollower(new IsFollowerRequest(), "/isfollower");
        }catch(RuntimeException |TweeterRemoteException | IOException e){
            e.printStackTrace();
        }

    }


    @Override
    protected boolean runTask() {
        LoginRequest loginRequest = new LoginRequest(username,password);
        boolean success = false;
        try {
            //warmUp();
            LoginResponse response = new ServerFacade().login(loginRequest, URL_PATH);
            this.user = response.getUser();
            this.authToken = response.getAuthToken();
            success = true;
            BackgroundTaskUtils.loadImage(this.user);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }

        return success;
    }
}

