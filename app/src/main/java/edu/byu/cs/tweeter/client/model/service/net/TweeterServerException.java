package edu.byu.cs.tweeter.client.model.service.net;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;

import java.util.List;

public class TweeterServerException extends TweeterRemoteException {


    public TweeterServerException(String message, String remoteExceptionType, List<String> remoteStakeTrace) {
        super(message, remoteExceptionType, remoteStakeTrace);
    }
}
