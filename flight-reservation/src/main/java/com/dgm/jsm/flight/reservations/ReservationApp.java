package com.dgm.jsm.flight.reservations;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReservationApp {

    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/requestQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext(JMSContext.SESSION_TRANSACTED)) {

            final JMSConsumer consumer = jmsContext.createConsumer(queue);
            final ObjectMessage objectMessage = (ObjectMessage) consumer.receive();
            System.out.println(objectMessage.getObject());

            jmsContext.commit();
        }
    }
}
