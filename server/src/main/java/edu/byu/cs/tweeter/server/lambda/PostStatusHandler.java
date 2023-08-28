package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;


import edu.byu.cs.tweeter.model.net.request.BatchStatusRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.JsonSerializer;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;
import edu.byu.cs.tweeter.server.service.StatusService;

public class PostStatusHandler implements RequestHandler<PostStatusRequest, PostStatusResponse> {
    @Override
    public PostStatusResponse handleRequest(PostStatusRequest request, Context context) {

        AbstractFactory factory =  HandleConfig.getInstance();
        String QURL = "https://sqs.us-west-2.amazonaws.com/707456696007/queue_one";

        BatchStatusRequest batchStatusRequest = new BatchStatusRequest();
        batchStatusRequest.setFeed(request.getStatusStory().getPost());
        batchStatusRequest.setUserWhoPosted(request.getStatusStory().getUser().getAlias());

        SendMessageRequest messageRequest = new SendMessageRequest()
                .withQueueUrl(QURL)
                .withMessageBody(JsonSerializer.serialize(batchStatusRequest));
        AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        SendMessageResult send_msg_result = sqs.sendMessage(messageRequest);

        if(send_msg_result != null){
            System.out.println(send_msg_result.getMessageId()+" Going to post the Story");
            return new StatusService(factory).postStatus(request);
        }

        System.out.println("Failed in the Post Status Handler-- SQS error");
        return new PostStatusResponse(false,"Failed to Post");
    }
}
