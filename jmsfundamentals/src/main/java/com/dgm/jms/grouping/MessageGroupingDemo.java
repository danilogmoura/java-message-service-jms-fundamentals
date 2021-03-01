package com.dgm.jms.grouping;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MessageGroupingDemo {

    public static void main(String[] args) throws NamingException, JMSException {

        final InitialContext initialContext = new InitialContext();
        final Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            final JMSProducer producer = jmsContext.createProducer();

            int count = 10;
            TextMessage[] messages = new TextMessage[count];

            for (int i = 0; i < count; i++) {
                messages[i] = jmsContext.createTextMessage("Group-0 message" + i);
                messages[i].setStringProperty("JMSXGroupID", "Group-0");
                producer.send(queue, messages[i]);
            }
        }
    }
}
