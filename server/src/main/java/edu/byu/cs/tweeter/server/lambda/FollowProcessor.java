package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import edu.byu.cs.tweeter.model.net.request.BatchStatusRequest;
import edu.byu.cs.tweeter.model.net.request.FollowersRequest;
import edu.byu.cs.tweeter.model.net.request.PostStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.JsonSerializer;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;
import edu.byu.cs.tweeter.server.service.StatusService;

import java.util.List;

public class FollowProcessor implements RequestHandler<SQSEvent, PostStatusResponse> {

    @Override
    public PostStatusResponse handleRequest(SQSEvent input, Context context) {

        AbstractFactory factory =  HandleConfig.getInstance();
        String QURL = "https://sqs.us-west-2.amazonaws.com/707456696007/queue_two";
        List<BatchStatusRequest> batchStatusRequests = null;
        BatchStatusRequest batchRequest = null;

        for (SQSEvent.SQSMessage msg : input.getRecords()) {

            batchRequest = JsonSerializer.deserialize(msg.getBody(), BatchStatusRequest.class);
            System.out.println("Passed the request in the FollowProcessor");
        }

        batchStatusRequests = new StatusService(factory).prepareBatches(batchRequest);
        SendMessageResult send_msg_result = null;
        for(BatchStatusRequest request: batchStatusRequests){
            SendMessageRequest messageRequest = new SendMessageRequest()
                    .withQueueUrl(QURL)
                    .withMessageBody(JsonSerializer.serialize(request));
            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
            send_msg_result = sqs.sendMessage(messageRequest);
        }

        if(send_msg_result != null){
            return new PostStatusResponse(true, "FORWARDED TO POSTS QUEUE SUCCESSFULLY");
        }


        return new PostStatusResponse(false, "Message was not forwarded to the queue and there is an error");
    }
}
