package com.dgm.jms.basics;

import javax.jms.*;
import javax.naming.InitialContext;

public class FirstTopic {

    public static void main(String[] args) throws Exception {


        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup("topic/myTopic");

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession();
        MessageProducer producer = session.createProducer(topic);

        MessageConsumer consumer1 = session.createConsumer(topic);
        MessageConsumer consumer2 = session.createConsumer(topic);

        TextMessage textMessage = session.createTextMessage("All the power is with in me. I can do anything and everything.");
        producer.send(textMessage);

        connection.start();

        TextMessage message1 = (TextMessage) consumer1.receive();
        System.out.printf("Consumer 1 message received: %s\n", message1.getText());

        TextMessage message2 = (TextMessage) consumer2.receive();
        System.out.printf("Consumer 2 message received: %s\n", message2.getText());

        initialContext.close();
        connection.close();
    }
}
