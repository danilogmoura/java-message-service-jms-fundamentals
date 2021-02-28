package com.dgm.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class RequestReplyDemo {

    public static void main(String[] args) throws Exception {

        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
        Queue replayQueue = (Queue) initialContext.lookup("queue/replyQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();
            producer.send(requestQueue, "Arise Awake and stop not till the goal is reached.");

            JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            String messageReceived = consumer.receiveBody(String.class);
            System.out.println(messageReceived);

            JMSProducer replayProducer = jmsContext.createProducer();
            replayProducer.send(replayQueue, "You are awesome!!!");

            JMSConsumer replayConsumer = jmsContext.createConsumer(replayQueue);
            System.out.println(replayConsumer.receiveBody(String.class));
        }
    }
}
