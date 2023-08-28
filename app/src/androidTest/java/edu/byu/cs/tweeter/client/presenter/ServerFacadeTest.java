package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.client.util.FakeData;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.FollowerCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.FollowerCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowersResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;


public class ServerFacadeTest {

    private FakeData fakeData;
    private User user;
    private ServerFacade serverFacade;
    private AuthToken authToken;

    public void setup() {
        fakeData = new FakeData();
        user = fakeData.getFirstUser();
        serverFacade = new ServerFacade();
        authToken = new AuthToken();

    }

    @Test
    public void registerTest() {
        setup();
        User expectedUser = user;

        RegisterResponse expected = new RegisterResponse(expectedUser, authToken,true,"Register Succeed");
        RegisterRequest request = new RegisterRequest(expectedUser.getFirstName(), expectedUser.getLastName(), expectedUser.getAlias(), "password", expectedUser.getImageUrl());

        try {
            RegisterResponse actual = serverFacade.register(request, "/register");

            Assertions.assertTrue(actual.isSuccess());
            Assertions.assertNull(actual.getMessage());

            Assertions.assertEquals(expected.getAuthToken().getClass(), actual.getAuthToken().getClass());
            Assertions.assertEquals(expected.getClass(), actual.getClass());

            User actualUser = actual.getUser();

            Assertions.assertEquals(expectedUser.getImageUrl(), actualUser.getImageUrl());
            Assertions.assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
            Assertions.assertEquals(expectedUser.getLastName(), actualUser.getLastName());
            Assertions.assertEquals(expectedUser.getAlias(), actualUser.getAlias());
            Assertions.assertEquals(expectedUser.getImageBytes(), actualUser.getImageBytes());
            Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        } catch (IOException e) {
            Assertions.assertTrue(false, "Caught an IO exception: " + e.getMessage());
        } catch (TweeterRemoteException e) {
            Assertions.assertTrue(false, "Caught a TweeterRemoteException: " + e.getMessage());
        }
    }

    @Test
    public void getFollowersTest() {
        setup();
        FollowersRequest request = new FollowersRequest();
        request.setLimit(10);
        request.setFollowerAlias(user.getAlias());
        FollowersResponse expected = new FollowersResponse(fakeData.getFakeUsers(), true);

        try {
            FollowersResponse actual = serverFacade.getFollowers(request, "/getfollowers");

            Assertions.assertEquals(expected.isSuccess(), actual.isSuccess());
            Assertions.assertEquals(expected.getMessage(), actual.getMessage());
            Assertions.assertEquals(expected.getHasMorePages(), actual.getHasMorePages());

            List<User> expectedUsers = expected.getFollowers();
            List<User> actualUsers = actual.getFollowers();

            for (int i = 0; i < actualUsers.size(); i++) {
                User expectedUser = expectedUsers.get(i);
                User actualUser = actualUsers.get(i);

                Assertions.assertEquals(expectedUser.getImageUrl(), actualUser.getImageUrl());
                Assertions.assertEquals(expectedUser.getFirstName(), actualUser.getFirstName());
                Assertions.assertEquals(expectedUser.getLastName(), actualUser.getLastName());
                Assertions.assertEquals(expectedUser.getAlias(), actualUser.getAlias());
                Assertions.assertEquals(expectedUser.getImageBytes(), actualUser.getImageBytes());
                Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
            }

        } catch (IOException e) {
            Assertions.assertTrue(false, "Caught an IO exception: " + e.getMessage());
        } catch (TweeterRemoteException e) {
            Assertions.assertTrue(false, "Caught a TweeterRemoteException: " + e.getMessage());
        }
    }

    @Test
    public void getFollowingCountTest() {
        setup();
        FollowingCountRequest request = new FollowingCountRequest();
        request.setUserName(user.getAlias());
        FollowingCountResponse expected = new FollowingCountResponse(20,true,"Got Followers");

        try {
            FollowingCountResponse actual = serverFacade.getFollowingCount(request, "/getfollowingcount");

            Assertions.assertEquals(expected.isSuccess(),actual.isSuccess());
            Assertions.assertEquals(expected.getMessage(),actual.getMessage());
            Assertions.assertEquals(expected.getCount(), actual.getCount());

        } catch (IOException e) {
            Assertions.assertTrue(false, "Caught an IO exception: " + e.getMessage());
        } catch (TweeterRemoteException e) {
            Assertions.assertTrue(false, "Caught a TweeterRemoteException: " + e.getMessage());
        }
    }

    @Test
    public void getFollowersCountTest() {
        setup();
        FollowerCountRequest request = new FollowerCountRequest();
        request.setUserName(user.getAlias());
        FollowerCountResponse expected = new FollowerCountResponse(20,true,"Got Follower");

        try {
            FollowerCountResponse actual = serverFacade.getFollowersCount(request, "/getfollowerscount");

            Assertions.assertEquals(expected.isSuccess(),actual.isSuccess());
            Assertions.assertEquals(expected.getMessage(),actual.getMessage());
            Assertions.assertEquals(expected.getCount(), actual.getCount());

        } catch (IOException e) {
            Assertions.assertTrue(false, "Caught an IO exception: " + e.getMessage());
        } catch (TweeterRemoteException e) {
            Assertions.assertTrue(false, "Caught a TweeterRemoteException: " + e.getMessage());
        }

    }

}
