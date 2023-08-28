package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.client.presenter.MainActivityPresenter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.request.LoginRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;
import edu.byu.cs.tweeter.model.net.response.LoginResponse;

public class IntegrationTest {

    private ServerFacade serverFacade;
    private MainActivityPresenter.MainView mainViewMock;
    private MainActivityPresenter mainPresenterSpy;
    private CountDownLatch countDownLatch;

    private void resetCountDownLatch() {
        countDownLatch = new CountDownLatch(1);
    }

    private void awaitCountDownLatch() throws InterruptedException {
        countDownLatch.await();
        resetCountDownLatch();
    }

    @Before
    public void setup() {
        serverFacade = new ServerFacade();

        mainViewMock = Mockito.mock(MainActivityPresenter.MainView.class);
        mainPresenterSpy = Mockito.spy(new MainActivityPresenter(mainViewMock, null));

        resetCountDownLatch();
    }

    @Test
    public void postStatusTest() throws IOException, TweeterRemoteException, InterruptedException {


        AuthToken authToken = null;
        LoginRequest loginRequest = new LoginRequest("@userA", "1");
        LoginResponse loginResponse = serverFacade.login(loginRequest, "/login");

        User currUser = loginResponse.getUser();
        authToken = loginResponse.getAuthToken();

        assertEquals("@userA", currUser.getAlias());
        assertNotNull(authToken);


        Answer<Void> server = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                countDownLatch.countDown();
                return null;
            }
        };

        Mockito.doAnswer(server).when(mainViewMock).displayInfoMessage("Successfully Posted! Post More:)");
        mainPresenterSpy.postStatus("post", currUser, authToken);
        awaitCountDownLatch();

        Mockito.verify(mainViewMock).displayInfoMessage("Posting Status...");
        Mockito.verify(mainViewMock).displayInfoMessage("Successfully Posted! Post More:)");


        GetStoryRequest getStoryRequest = new GetStoryRequest(currUser.getAlias(), 1, null);
        GetStoryResponse storyResponse = serverFacade.getStory(getStoryRequest, "/getstory");

        Status status = storyResponse.getStoryList().get(0);
        User statusUser = status.getUser();

        assertEquals(currUser, statusUser);
        assertEquals("post", status.getPost());
        assertEquals(0, status.getMentions().size());
        assertEquals(0, status.getUrls().size());



    }

}