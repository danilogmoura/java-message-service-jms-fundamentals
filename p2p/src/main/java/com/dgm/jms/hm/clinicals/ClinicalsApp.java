package com.dgm.jms.hm.clinicals;

import com.dgm.jms.hm.model.Patient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ClinicalsApp {

    public static void main(String[] args) throws NamingException {

        final InitialContext initialContext = new InitialContext();
        final Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
        final Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {
            final JMSProducer producer = jmsContext.createProducer();

            final Patient patient = new Patient();
            patient.setId(123);
            patient.setName("Sophia");
            patient.setInsuranceProvider("Blue Cross Blue Shield");
            patient.setCopay(30d);
            patient.setAmountToBePayed(500d);

            final ObjectMessage objectMessage = jmsContext.createObjectMessage();
            objectMessage.setObject(patient);

            producer.send(requestQueue, objectMessage);

            final JMSConsumer consumer = jmsContext.createConsumer(replyQueue);
            final MapMessage messageReceive = (MapMessage) consumer.receive(30000);
            System.out.printf("Patient eligibility is: %d", messageReceive.getBoolean("eligible"));
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}