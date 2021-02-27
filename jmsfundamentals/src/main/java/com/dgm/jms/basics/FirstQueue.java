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
            System.out.printf("Message Sent: %s\n", message.getText());

            //Consumer
            session.createConsumer(queue);

            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();

            TextMessage messageReceived = (TextMessage) consumer.receive(5000);
            System.out.printf("Message Received: %s\n", messageReceived.getText());

        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }

    }
}
