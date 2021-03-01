package com.dgm.jms.claimmanagement;

import com.dgm.jms.claimmanagement.model.Claim;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ClaimManagement {

    public static void main(String[] args) throws NamingException {

        final InitialContext initialContext = new InitialContext();
        final Queue queue = (Queue) initialContext.lookup("queue/claimQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            Claim claim = new Claim(
                    1,
                    "John",
                    "dyna",
                    "blue cross",
                    1000);

            ObjectMessage objectMessage = jmsContext.createObjectMessage(claim);

            final JMSProducer producer = jmsContext.createProducer();
            final JMSConsumer consumer = jmsContext.createConsumer(queue, "");

        }
    }
}
