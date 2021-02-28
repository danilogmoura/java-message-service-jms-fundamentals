package com.dgm.jms.messagestructure;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessagePriority {

    public static void main(String[] args) throws NamingException {

        InitialContext context = new InitialContext();
        Queue queue = (Queue) context.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();

            String[] messages = new String[3];
            messages[0] = "Message One";
            messages[1] = "Message two";
            messages[2] = "Message Three";

//            producer.setPriority(3);
            producer.send(queue, messages[0]);

//            producer.setPriority(1);
            producer.send(queue, messages[1]);

//            producer.setPriority(9);
            producer.send(queue, messages[2]);

            JMSConsumer consumer = jmsContext.createConsumer(queue);

            for (int i = 0; i < 3; i++) {
                Message receivedMessage = consumer.receive();
                System.out.printf("Priority: %s\n", receivedMessage.getJMSPriority());
                System.out.println(receivedMessage.getBody(String.class));
                System.out.println();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
