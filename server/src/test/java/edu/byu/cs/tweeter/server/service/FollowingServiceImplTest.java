package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.dynamo.FollowDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.StatusDAO;
import edu.byu.cs.tweeter.server.dao.dynamo.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.FollowingCountRequest;
import edu.byu.cs.tweeter.model.net.request.FollowingRequest;
import edu.byu.cs.tweeter.model.net.request.GetFeedRequest;
import edu.byu.cs.tweeter.model.net.request.GetUserRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.FollowingCountResponse;
import edu.byu.cs.tweeter.model.net.response.FollowingResponse;
import edu.byu.cs.tweeter.model.net.response.GetFeedResponse;
import edu.byu.cs.tweeter.model.net.response.GetUserResponse;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;

public class FollowingServiceImplTest {

    private FollowDAO f;

    private FollowingRequest request;
    private FollowingResponse expectedResponse;
    private FollowDAO mockFollowingDAO;
    private StatusDAO mockStatusDAO;
    private UserDAO mockUserDAO;
    private FollowService followingServiceImplSpy;
    private UserService userServiceSpy;
    private StatusService statusServiceSpy;
    private RegisterRequest registerRequest;
    private GetUserRequest getUserRequest;
    private GetFeedRequest getFeedRequest;
    private PostStatusRequest postStatusRequest;
//    private FollowingRequest followersRequest;
    private FollowingCountRequest followerCountRequest;
    private RegisterResponse expectedRegisterResponse;
    private GetUserResponse expectedGetUSerResponse;
    private GetFeedResponse expectedGetFeedResponse;
    private PostStatusResponse expectedPostStatusResponse;
    private FollowingResponse expectedFollowersResponse;
    private FollowingCountResponse expectedFollowersCountResponse;

    @BeforeEach
    public void setup() {

        List<Status> statusList = new ArrayList<>();
        final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
        final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";
        final User user1 = new User("Allen", "Anderson", "@allen", MALE_IMAGE_URL);
        final User user2 = new User("Amy", "Ames", "@amy", FEMALE_IMAGE_URL);
        final User user3 = new User("Bob", "Bobson", "@bob", MALE_IMAGE_URL);
        final User user4 = new User("Bonnie", "Beatty", "@bonnie", FEMALE_IMAGE_URL);
        final User user5 = new User("Chris", "Colston", "@chris", MALE_IMAGE_URL);
        final User user6 = new User("Cindy", "Coats", "@cindy", FEMALE_IMAGE_URL);
        final User user7 = new User("Dan", "Donaldson", "@dan", MALE_IMAGE_URL);
        final User user8 = new User("Dee", "Dempsey", "@dee", FEMALE_IMAGE_URL);
        final User user9 = new User("Elliott", "Enderson", "@elliott", MALE_IMAGE_URL);
        final User user10 = new User("Elizabeth", "Engle", "@elizabeth", FEMALE_IMAGE_URL);
        final User user11 = new User("Frank", "Frandson", "@frank", MALE_IMAGE_URL);
        final User user12 = new User("Fran", "Franklin", "@fran", FEMALE_IMAGE_URL);
        final User user13 = new User("Gary", "Gilbert", "@gary", MALE_IMAGE_URL);
        final User user14 = new User("Giovanna", "Giles", "@giovanna", FEMALE_IMAGE_URL);
        final User user15 = new User("Henry", "Henderson", "@henry", MALE_IMAGE_URL);
        final User user16 = new User("Helen", "Hopwell", "@helen", FEMALE_IMAGE_URL);
        final User user17 = new User("Igor", "Isaacson", "@igor", MALE_IMAGE_URL);
        final User user18 = new User("Isabel", "Isaacson", "@isabel", FEMALE_IMAGE_URL);
        final User user19 = new User("Justin", "Jones", "@justin", MALE_IMAGE_URL);
        final User user20 = new User("Jill", "Johnson", "@jill", FEMALE_IMAGE_URL);
        User user21 = new User("John", "Brown", "@john", MALE_IMAGE_URL);

        List<User> allUsers = Arrays.asList(
                user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11,
                user12, user13, user14, user15, user16, user17, user18, user19, user20, user21
        );


        Calendar calendar = new GregorianCalendar();

        int counter = 0;
        for (int i = 0; i < 2; ++i) {
            counter++;
            for (int j = 0; j < allUsers.size(); ++j) {
                counter++;
                User sender = allUsers.get(j);
                User mention = ((j < allUsers.size() - 1) ? allUsers.get(j + 1) : allUsers.get(0));
                List<String> mentions = Arrays.asList(mention.getAlias());
                String url = "https://byu.edu";
                List<String> urls = Arrays.asList(url);
                String post = "Post " + i + " " + j +
                        "\nMy friend " + mention.getAlias() + " likes this website" +
                        "\n" + url;
                calendar.add(Calendar.MINUTE, 1);
                String datetime = calendar.getTime().toString();
                Status status = new Status(post, sender, datetime, urls, mentions,counter);
                statusList.add(status);
            }


            User currentUser = new User("FirstName", "LastName", null);

            User resultUser1 = new User("FirstName1", "LastName1",
                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            User resultUser2 = new User("FirstName2", "LastName2",
                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
            User resultUser3 = new User("FirstName3", "LastName3",
                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

            // Setup a request object to use in the tests
            request = new FollowingRequest(currentUser.getAlias(), 3, null);
            getFeedRequest  = new GetFeedRequest("fake get feed request");
            followerCountRequest = new FollowingCountRequest("fake username");
            //postStatusRequest = new PostStatusRequest(new Status().toString());
            registerRequest = new RegisterRequest("first", "last", "alias", "PWD", "fakeURL");
            // Setup a mock FollowingDAO that will return known responses
            expectedResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
            expectedRegisterResponse = new RegisterResponse(true, "registered fake user ok");
            expectedGetUSerResponse = new GetUserResponse(new User("new", "fake", "pic"));
            expectedPostStatusResponse = new PostStatusResponse(true, "status added successfully");
            expectedFollowersCountResponse = new FollowingCountResponse();
            expectedGetFeedResponse =  new GetFeedResponse(new User("new", "fake", "fakeImage"), 10, statusList.get(0), statusList);
//            mockFollowingDAO = Mockito.mock(FollowDAO.class);
//            mockUserDAO = Mockito.mock(UserDAO.class);
//            mockStatusDAO = Mockito.mock(StatusDAO.class);
//            Mockito.when(mockFollowingDAO.getFollowees(request)).thenReturn(expectedResponse);
//            Mockito.when(mockUserDAO.getUser(getUserRequest)).thenReturn(expectedGetUSerResponse);
//            Mockito.when(mockUserDAO.register(registerRequest)).thenReturn(expectedRegisterResponse);
//            Mockito.when(mockStatusDAO.getFeed(getFeedRequest)).thenReturn(expectedGetFeedResponse);
//            Mockito.when(mockStatusDAO.postStatus(postStatusRequest)).thenReturn(expectedPostStatusResponse);
//            Mockito.when(mockFollowingDAO.getFollowingCount(followerCountRequest)).thenReturn(expectedFollowersCountResponse);
            followingServiceImplSpy = Mockito.spy(FollowService.class);
            statusServiceSpy = Mockito.spy(StatusService.class);
            userServiceSpy = Mockito.spy(UserService.class);
            //Added GetStatusDAO to the StatusDAO
          //  Mockito.when(statusServiceSpy.getStatusDAO()).thenReturn(mockStatusDAO);
            //Mockito.when(followingServiceImplSpy.getFollowingDAO()).thenReturn(mockFollowingDAO);
        }
    }

    /**
     * Verify that the {@link FollowService#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link } class.
     */

    //@link followDAO
    @Test
    public void testGetFollowees_validRequest_correctResponse() {
        FollowingResponse response = followingServiceImplSpy.getFollowees(request);
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    public void testRegister_validRequest_correctResponse() throws ParseException {

        RegisterResponse  response = userServiceSpy.register(registerRequest);
        Assertions.assertEquals(expectedRegisterResponse, response);

    }

    @Test
    public void testGestUser_validRequest_correctResponse() throws ParseException {
        getUserRequest = new GetUserRequest(null, "@sherif");
        GetUserResponse response = userServiceSpy.getUser(getUserRequest);
        Assertions.assertEquals(expectedGetUSerResponse, response);
    }
    @Test
    public void testGetFeed_validRequest_correctResponse()
    {
        GetFeedResponse localResponse = statusServiceSpy.getFeed(getFeedRequest);
        Assertions.assertEquals(localResponse, expectedGetFeedResponse);
    }
    @Test
    public void testPostStatus_validRequest_correctResponse()
    {
        PostStatusResponse response = statusServiceSpy.postStatus(postStatusRequest);
        Assertions.assertEquals(expectedPostStatusResponse, response);
    }

    @Test
    public void testGetFollowers_validRequest_correctResponse()
    {
        FollowingResponse response = followingServiceImplSpy.getFollowees(request);
        Assertions.assertEquals(response, expectedResponse);
    }

    @Test
    public void testGetFollowersCount_validRequest_correctResponse()
    {
        FollowingCountResponse response = followingServiceImplSpy.getFollowingCount(followerCountRequest);
        Assertions.assertEquals(response, expectedFollowersCountResponse);
    }
}
