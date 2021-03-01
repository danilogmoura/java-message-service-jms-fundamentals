package com.dgm.jms.guaranteedmessaging;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageConsumer {

    public static void main(String[] args) throws NamingException, JMSException {

        final InitialContext initialContext = new InitialContext();
        final Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {


            final JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            final TextMessage message = (TextMessage) consumer.receive();
            System.out.println(message.getText());
        }
    }
}
