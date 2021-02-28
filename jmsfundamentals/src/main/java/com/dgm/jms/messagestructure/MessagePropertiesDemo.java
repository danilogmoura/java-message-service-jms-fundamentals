package com.dgm.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;

public class MessagePropertiesDemo {

    public static void main(String[] args) throws Exception {

        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");
        Queue expiryQueue = (Queue) initialContext.lookup("queue/expiryQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();
            producer.setDeliveryDelay(300);
            TextMessage message = jmsContext.createTextMessage("Arise Awake and stop not till the goal is reached");
            message.setBooleanProperty("loggedIn", true);
            message.setStringProperty("userToken", "abc123");
            producer.send(queue, message);

            Message messageReceived = jmsContext.createConsumer(queue).receive(500);
            System.out.println(messageReceived);
            System.out.println(messageReceived.getBooleanProperty("loggedIn"));
            System.out.println(messageReceived.getStringProperty("userToken"));
        }
    }
}
