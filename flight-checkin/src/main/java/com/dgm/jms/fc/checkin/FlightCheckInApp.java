package com.dgm.jms.fc.checkin;

import com.dgm.jms.fc.model.Passenger;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FlightCheckInApp {

    public static void main(String[] args) throws NamingException, JMSException {

        final InitialContext initialContext = new InitialContext();

        final Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            final Passenger passenger = new Passenger(
                    123,
                    "Sophia",
                    "Moraes",
                    "sophiagm@gmail.com",
                    "+55 (19) 99999-99-99"
            );

            final TemporaryQueue temporaryQueue = jmsContext.createTemporaryQueue();

            final ObjectMessage objectMessage = jmsContext.createObjectMessage(passenger);
            objectMessage.setJMSReplyTo(temporaryQueue);

            final JMSProducer producer = jmsContext.createProducer();
            producer.send(requestQueue, objectMessage);

            //consumer
            final JMSConsumer consumer = jmsContext.createConsumer(objectMessage.getJMSReplyTo());
            final MapMessage messageReceive = (MapMessage) consumer.receive(30000);

            System.out.printf("Is passenger ticker reserved: %b\n", messageReceive.getBoolean("isReserved"));

            //load balancing
            for (int i = 0; i < 10; i++) {
                producer.send(requestQueue, objectMessage);
            }
        }
    }
}
