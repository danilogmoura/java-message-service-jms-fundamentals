package com.dgm.jms.fc.reservations.listeners;

import com.dgm.jms.fc.model.Passenger;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;

public class ReservationSystemListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        final ObjectMessage objectMessage = (ObjectMessage) message;

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            final MapMessage mapMessage = jmsContext.createMapMessage();
            mapMessage.setJMSCorrelationID(objectMessage.getJMSMessageID());
            mapMessage.setBoolean("isReserved", false);

            Passenger passenger = (Passenger) objectMessage.getObject();
            System.out.println("Passenger: " + passenger);

            if (passenger != null) {
                mapMessage.setBoolean("isReserved", true);
            }

            final JMSProducer jmsProducer = jmsContext.createProducer();
            jmsProducer.send(objectMessage.getJMSReplyTo(), mapMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
