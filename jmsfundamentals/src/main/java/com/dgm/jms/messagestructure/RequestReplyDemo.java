package com.dgm.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import java.util.HashMap;
import java.util.Map;

public class RequestReplyDemo {

    public static void main(String[] args) throws Exception {

        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/requestQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();
            TemporaryQueue replayQueue = jmsContext.createTemporaryQueue();
            TextMessage textMessage = jmsContext.createTextMessage("Arise Awake and stop not till the goal is reached.");
            textMessage.setJMSReplyTo(replayQueue);
            producer.send(queue, textMessage);
            System.out.println(textMessage.getJMSMessageID());

            Map<String, TextMessage> requestMessage = new HashMap<>();
            requestMessage.put(textMessage.getJMSMessageID(), textMessage);

            JMSConsumer consumer = jmsContext.createConsumer(queue);
            TextMessage messageReceived = (TextMessage) consumer.receive();
            System.out.println(messageReceived.getText());
            System.out.println();

            JMSProducer replayProducer = jmsContext.createProducer();
            TextMessage replayMessage = jmsContext.createTextMessage("You are awesome!!!");
            replayMessage.setJMSCorrelationID(messageReceived.getJMSMessageID());
            replayProducer.send(messageReceived.getJMSReplyTo(), replayMessage);

            JMSConsumer replayConsumer = jmsContext.createConsumer(replayQueue);
            TextMessage replayReceived = (TextMessage) replayConsumer.receive();
            System.out.println(replayReceived.getJMSCorrelationID());
            System.out.println(replayReceived.getText());
            System.out.println(requestMessage.get(replayReceived.getJMSCorrelationID()).getText());
        }
    }
}
