package com.dgm.jms.basics;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {

    public static void main(String[] args) {

        InitialContext initialContext = null;

        try {
            initialContext = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            Connection connection = factory.createConnection();

            Session session = connection.createSession();

            Queue queue = (Queue) initialContext.lookup("queue/myQueue=");
            MessageProducer producer = session.createProducer(queue);

            TextMessage message = session.createTextMessage("I am the creator of my destiny");
            producer.send(message);
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }

    }
}
