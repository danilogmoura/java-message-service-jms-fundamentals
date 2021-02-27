package com.dgm.jms.basics;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Enumeration;

public class QueuBrowserDemo {

    public static void main(String[] args) {

        InitialContext initialContext = null;
        Connection connection = null;

        try {
            initialContext = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
            connection = factory.createConnection();

            Session session = connection.createSession();

            Queue queue = (Queue) initialContext.lookup("queue/myQueue");
            MessageProducer producer = session.createProducer(queue);

            TextMessage message1 = session.createTextMessage("Message 1");
            TextMessage message2 = session.createTextMessage("Message 2");

            producer.send(message1);
            producer.send(message2);

            //Consumer
            QueueBrowser browser = session.createBrowser(queue);
            Enumeration messagesEnum = browser.getEnumeration();

            while (messagesEnum.hasMoreElements()) {
                TextMessage eachMessage = (TextMessage) messagesEnum.nextElement();
                System.out.println(String.format("Browsing: %s", eachMessage.getText()));
            }

            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();

            TextMessage messageReceived = (TextMessage) consumer.receive(5000);
            System.out.printf("Message Received: %s\n", messageReceived.getText());

            messageReceived = (TextMessage) consumer.receive(5000);
            System.out.printf("Message Received: %s\n", messageReceived.getText());

        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        } finally {
            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
