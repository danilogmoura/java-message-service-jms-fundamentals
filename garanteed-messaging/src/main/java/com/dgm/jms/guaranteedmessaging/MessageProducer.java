package com.dgm.jms.guaranteedmessaging;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageProducer {

    public static void main(String[] args) throws NamingException {

        final InitialContext initialContext = new InitialContext();
        final Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {

            final JMSProducer producer = jmsContext.createProducer();
            producer.send(requestQueue, "Message 1");
        }
    }
}
