package com.dgm.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;

public class RequestReplyDemo {

    public static void main(String[] args) throws Exception {

        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
        Queue replayQueue = (Queue) initialContext.lookup("queue/replyQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();
            TextMessage textMessage = jmsContext.createTextMessage("Arise Awake and stop not till the goal is reached.");
            textMessage.setJMSReplyTo(replayQueue);
            producer.send(requestQueue, textMessage);

            JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            TextMessage messageReceived = (TextMessage) consumer.receive();
            System.out.println(messageReceived.getText());

            JMSProducer replayProducer = jmsContext.createProducer();
            replayProducer.send(messageReceived.getJMSReplyTo(), "You are awesome!!!");

            JMSConsumer replayConsumer = jmsContext.createConsumer(replayQueue);
            System.out.println(replayConsumer.receiveBody(String.class));
        }
    }
}
