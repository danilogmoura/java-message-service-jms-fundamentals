package com.dgm.javaee.jms.mdbs;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

public class MyMdb implements MessageListener {

    private static Logger LOGGER = Logger.getLogger(MyMdb.class.toString());

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                LOGGER.info("Received Message is: " + message);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
