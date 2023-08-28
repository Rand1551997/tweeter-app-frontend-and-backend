package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import edu.byu.cs.tweeter.server.dao.factories.AbstractFactory;
import edu.byu.cs.tweeter.server.dao.factories.DBFactory;
import edu.byu.cs.tweeter.server.dao.factories.DummyFactory;

public class HandleConfig {

    private static AbstractFactory factory;

    public static AbstractFactory getInstance() {
        if (factory == null) {
//            factory = new DummyFactory();
            factory = new DBFactory();
        }
        return factory;
    }
}
