package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.net.request.BatchStatusRequest;
import edu.byu.cs.tweeter.model.net.response.PostStatusResponse;
import edu.byu.cs.tweeter.server.JsonSerializer;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;
import edu.byu.cs.tweeter.server.service.StatusService;

public class PostProcessor implements RequestHandler<SQSEvent, PostStatusResponse> {
    @Override
    public PostStatusResponse handleRequest(SQSEvent input, Context context) {
        AbstractFactory factory =  HandleConfig.getInstance();

        for (SQSEvent.SQSMessage msg : input.getRecords()) {

            StatusService statusService = new StatusService(factory);
            BatchStatusRequest request = JsonSerializer.deserialize(msg.getBody(), BatchStatusRequest.class);

            if(!statusService.batchPostStatus(request)){
                return new PostStatusResponse(false,"Error in Writing the Batch");
            }
        }

        return new PostStatusResponse(true,"Successfully Posted the Feed :)");
    }
}
