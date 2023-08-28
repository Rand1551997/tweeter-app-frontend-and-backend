package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.dynamo.StatusDAO;
import edu.byu.cs.tweeter.server.dao.daoInterfaces.iStatusDAO;
import edu.byu.cs.tweeter.server.dao.factories.DBFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.GetStoryRequest;
import edu.byu.cs.tweeter.model.net.response.GetStoryResponse;

public class StoryServiceTest {

    private StatusService statusServiceSpy;
    private DBFactory factoryMock;
    private iStatusDAO storyDAOMock;
    private List<Status> mockData;

    @BeforeEach
    public void setup() {

        factoryMock = Mockito.mock(DBFactory.class);
        storyDAOMock = Mockito.mock(StatusDAO.class);

        statusServiceSpy = Mockito.spy(new StatusService(factoryMock));
        Mockito.doReturn(true).when(statusServiceSpy).validateToken(Mockito.any());
        Mockito.doReturn(storyDAOMock).when(factoryMock).getStatusDAO();

        mockData = createMockData();
    }

    private List<Status> createMockData() {
        User user = new User("@user1");
        List<Status> mockData = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Status status = new Status("post" + i, user, "datetime" + i, null, null, i);
            mockData.add(status);
        }

        return mockData;
    }


    private void mockReturnGetStory(GetStoryRequest request) {

        if (request.getLastStatus() == null) {
            int endIndex = request.getLimit()-1 >= mockData.size() ? mockData.size() : request.getLimit();
            List newArray = new ArrayList(mockData.subList(0, endIndex));
            GetStoryResponse response = new GetStoryResponse(true, newArray, true, "Sucessfullly got stories");
            Mockito.doReturn(response).when(storyDAOMock).getStory(Mockito.any(),Mockito.any(),Mockito.any());
        }

        else {

            for (int i = 0; i < mockData.size(); i++) {

                if (request.getLastStatus().getPost().equals(mockData.get(i).getPost())) {
                    int endIndex = i + request.getLimit()-1 >= mockData.size() ? mockData.size() : i + request.getLimit();
                    List newArray = new ArrayList(mockData.subList(i, endIndex));

                    GetStoryResponse response = new GetStoryResponse(endIndex == mockData.size() ? false : true, newArray, true, "Sucessfullly got stories");
                    Mockito.doReturn(response).when(storyDAOMock).getStory(Mockito.any(),Mockito.any(),Mockito.any());
                    break;
                }
            }
        }
    }

    @Test
    public void getStory1_4() {

        System.out.println("h");
        GetStoryRequest request = new GetStoryRequest("@user1",4,null);
        mockReturnGetStory(request);
        GetStoryResponse actual = statusServiceSpy.getStory(request);

        Assertions.assertEquals(true, actual.getHasMorePages());

        List<Status> actualStories = actual.getStoryList();
        for (int i = 0; i < request.getLimit(); i++) {
            Assertions.assertEquals("post"+i, actualStories.get(i).getPost());
            Assertions.assertEquals("datetime"+i, actualStories.get(i).getDatetime());
            Assertions.assertEquals(null, actualStories.get(i).getMentions());
            Assertions.assertEquals(null, actualStories.get(i).getUrls());
            Assertions.assertEquals("@user1", actualStories.get(i).getUser().getAlias());
        }
    }

    @Test
    public void getStory4_8() {
        
        Status fourth = new Status("post4", new User(), null, null, null, 3);
        GetStoryRequest request = new GetStoryRequest("@user1",5,fourth);
        mockReturnGetStory(request);
        GetStoryResponse actual = statusServiceSpy.getStory(request);

        Assertions.assertEquals(true, actual.getHasMorePages());

        List<Status> actualStories = actual.getStoryList();
        for (int i = 4; i < 9; i++) {
            Assertions.assertEquals("post"+i, actualStories.get(i-4).getPost());
            Assertions.assertEquals("datetime"+i, actualStories.get(i-4).getDatetime());
            Assertions.assertEquals(null, actualStories.get(i-4).getMentions());
            Assertions.assertEquals(null, actualStories.get(i-4).getUrls());
            Assertions.assertEquals("@user1", actualStories.get(i-4).getUser().getAlias());
        }
    }

    @Test
    public void getStory6_10() {
        
        Status sixth = new Status("post6", new User(), null, null, null, 5);
        GetStoryRequest request = new GetStoryRequest("@user1",5,sixth);
        mockReturnGetStory(request);
        GetStoryResponse actual = statusServiceSpy.getStory(request);

        Assertions.assertEquals(false, actual.getHasMorePages());

        List<Status> actualStories = actual.getStoryList();
        
        for (int i = 6; i < 10; i++) {
            Assertions.assertEquals("post"+i, actualStories.get(i-6).getPost());
            Assertions.assertEquals("datetime"+i, actualStories.get(i-6).getDatetime());
            Assertions.assertEquals(null, actualStories.get(i-6).getMentions());
            Assertions.assertEquals(null, actualStories.get(i-6).getUrls());
            Assertions.assertEquals("@user1", actualStories.get(i-6).getUser().getAlias());
        }
    }
}