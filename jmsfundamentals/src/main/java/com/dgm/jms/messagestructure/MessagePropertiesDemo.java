package com.dgm.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;

public class MessagePropertiesDemo {

    public static void main(String[] args) throws Exception {

        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();
            producer.setDeliveryDelay(300);
            TextMessage textMessage = jmsContext.createTextMessage("Arise Awake and stop not till the goal is reached");
            textMessage.setBooleanProperty("loggedIn", true);
            textMessage.setStringProperty("userToken", "abc123");
            producer.send(queue, textMessage);

            Message messageReceived = jmsContext.createConsumer(queue).receive(500);
            System.out.println(messageReceived);
            System.out.println(messageReceived.getBooleanProperty("loggedIn"));
            System.out.println(messageReceived.getStringProperty("userToken"));
        }
    }
}
