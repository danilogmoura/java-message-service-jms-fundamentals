package com.dgm.jsm.flight.checkin;

import com.dgm.jsm.flight.checkin.model.FlightCheckin;
import com.github.javafaker.Faker;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CheckinApp {

    public static void main(String[] args) throws NamingException {

        final InitialContext initialContext = new InitialContext();
        final Queue queue = (Queue) initialContext.lookup("queue/requestQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext(JMSContext.SESSION_TRANSACTED)) {

            final JMSProducer producer = jmsContext.createProducer();
            final Faker faker = new Faker();
            for (int i = 0; i < 3; i++) {

                FlightCheckin flightCheckin = new FlightCheckin(
                        1 + i,
                        faker.country().capital(),
                        faker.name().name(),
                        faker.idNumber().ssnValid(),
                        faker.idNumber().ssnValid()
                );

                ObjectMessage objectMessage = jmsContext.createObjectMessage(flightCheckin);
                producer.send(queue, objectMessage);
            }
            jmsContext.commit();
        }

    }
}
