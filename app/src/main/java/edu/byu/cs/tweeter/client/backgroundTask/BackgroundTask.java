package edu.byu.cs.tweeter.client.backgroundTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import edu.byu.cs.tweeter.client.util.FakeData;

public abstract class BackgroundTask implements Runnable{

    private static final String LOG_TAG = "BackgoundTask";

    public static final String SUCCESS_KEY = "success";
    public static final String MESSAGE_KEY = "message";
    public static final String EXCEPTION_KEY = "exception";


    /**
     * Message handler that will receive task results.
     */
    protected Handler messageHandler;
    protected String error;

    protected BackgroundTask(Handler messageHandler) {this.messageHandler = messageHandler;}

    protected interface BundleLoader {
        void load(Bundle msgBundle);
    }

    @Override
    public void run() {
        try {
            if(runTask()){
                sendSuccessMessage();
            }
            else{
                sendFailedMessage();
            }

        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            sendExceptionMessage(ex);
        }
    }

    protected FakeData getFakeData() {
        return new FakeData();
    }

    protected abstract boolean runTask();

    protected void loadSuccessBundle(Bundle msgBundle){
        return;
    }



    protected void sendSuccessMessage() {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, true);
        loadSuccessBundle(msgBundle);
        sendMessage(msgBundle);

    }

    public void sendSuccessMessage(BundleLoader bundleLoader) {
        Bundle msgBundle = createSuccessBundle();
        bundleLoader.load(msgBundle);
        sendMessage(msgBundle);
    }

    private Bundle createSuccessBundle() {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, true);
        return msgBundle;
    }

    private Bundle createFailedBundle() {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, false);
        return msgBundle;
    }

    protected void sendFailedMessage() {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, false);
        msgBundle.putString(MESSAGE_KEY, error);

        sendMessage(msgBundle);
    }

    protected void sendFailedMessage(String message) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, false);
        msgBundle.putString(MESSAGE_KEY, message);

        sendMessage(msgBundle);
    }

    protected void sendExceptionMessage(Exception exception) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, false);
        msgBundle.putSerializable(EXCEPTION_KEY, exception);

        sendMessage(msgBundle);
    }

    protected void sendMessage(Bundle msgBundle){
        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);
    }
}
