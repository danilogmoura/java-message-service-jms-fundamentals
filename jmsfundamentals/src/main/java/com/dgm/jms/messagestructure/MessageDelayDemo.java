package com.dgm.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.naming.InitialContext;

public class MessageDelayDemo {

    public static void main(String[] args) throws Exception {

        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();
            producer.setDeliveryDelay(300);
            producer.send(queue, "Arise Awake and stop not till the goal is reached");

            Message messageReceived = jmsContext.createConsumer(queue).receive(500);
            System.out.println(messageReceived);
        }
    }
}
