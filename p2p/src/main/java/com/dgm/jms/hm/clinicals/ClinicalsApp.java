package com.dgm.jms.hm.clinicals;

import com.dgm.jms.hm.model.Patient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ClinicalsApp {

    public static void main(String[] args) throws NamingException {

        final InitialContext initialContext = new InitialContext();
        final Queue queue = (Queue) initialContext.lookup("request/questQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {
            final JMSProducer producer = jmsContext.createProducer();

            final ObjectMessage objectMessage = jmsContext.createObjectMessage();
            final Patient patient = new Patient();
            patient.setId(123);
            patient.setName("Sophia");
            patient.setInsuranceProvider("Blue Cross Blue Shield");
            patient.setCopay(30d);
            patient.setAmountToBePayed(500d);
            objectMessage.setObject(patient);

            producer.send(queue, objectMessage);

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
