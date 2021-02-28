package com.dgm.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class MessageExpirationDemo {

    public static void main(String[] args) throws Exception {

        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");
        Queue expiryQueue = (Queue) initialContext.lookup("queue/expiryQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();
            producer.setTimeToLive(2000);
            producer.send(queue, "Arise Awake and stop not till the goal is reached");

            Thread.sleep(5000);

            Message messageReceived = jmsContext.createConsumer(queue).receive(500);
            System.out.println(messageReceived);

            System.out.println("\nExpiry Queue");
            System.out.println(jmsContext.createConsumer(expiryQueue).receiveBody(String.class));
        }
    }
}
