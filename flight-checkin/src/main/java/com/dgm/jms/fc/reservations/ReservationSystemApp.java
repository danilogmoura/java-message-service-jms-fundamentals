package com.dgm.jms.fc.reservations;

import com.dgm.jms.fc.reservations.listeners.ReservationSystemListener;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ReservationSystemApp {

    public static void main(String[] args) throws NamingException {

        final InitialContext initialContext = new InitialContext();
        final Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            final JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            consumer.setMessageListener(new ReservationSystemListener());

            Thread.sleep(10000);

            final JMSConsumer consumer1 = jmsContext.createConsumer(requestQueue);
            final JMSConsumer consumer2 = jmsContext.createConsumer(requestQueue);

            for (int i = 0; i < 10; i += 2) {
                System.out.println("ConsumerOne: " + consumer1.receive());
                System.out.println("ConsumerTwo: " + consumer2.receive());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
