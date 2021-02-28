package com.dgm.jms.hm.eligibilitycheck.listeners;

import com.dgm.jms.hm.model.Patient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EligibilityCheckListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        final ObjectMessage objectMessage = (ObjectMessage) message;

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            final InitialContext initialContext = new InitialContext();
            final Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");

            final Patient patient = (Patient) objectMessage.getObject();

            final MapMessage mapMessage = jmsContext.createMapMessage();
            mapMessage.setBoolean("eligible", false);

            final String insuranceProvider = patient.getInsuranceProvider();
            System.out.printf("Insurance Provider: %s\n", insuranceProvider);

            if (insuranceProvider.equals("Blue Cross Blue Shield") || insuranceProvider.equals("United Health")) {
                System.out.printf("Patients Copay is: $%.2f, Amount to be payed: $%.2f\n", patient.getCopay(), patient.getAmountToBePayed());

                if (patient.getCopay() < 40 && patient.getAmountToBePayed() < 1000) {
                    mapMessage.setBoolean("eligible", true);
                }
            }

            final JMSProducer producer = jmsContext.createProducer();
            producer.send(replyQueue, mapMessage);
        } catch (JMSException | NamingException e) {
            e.printStackTrace();
        }
    }
}
