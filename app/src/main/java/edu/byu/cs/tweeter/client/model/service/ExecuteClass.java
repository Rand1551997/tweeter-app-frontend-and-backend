package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteClass <T extends BackgroundTask>{

    public void execute(T task){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(task);
    }
}
