package com.dgm.jms.claimmanagement;

import com.dgm.jms.claimmanagement.model.Claim;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ClaimManagement {

    public static void main(String[] args) throws NamingException, JMSException {

        final InitialContext initialContext = new InitialContext();
        final Queue queue = (Queue) initialContext.lookup("queue/claimQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            Claim claim = new Claim(
                    1,
                    "John",
                    "gyna",
                    "blue cross",
                    1000);

            ObjectMessage objectMessage = jmsContext.createObjectMessage(claim);
//            objectMessage.setIntProperty("hospitalId", claim.getHospitalId());
//            objectMessage.setDoubleProperty("claimAmount", claim.getClaimAmount());
//            objectMessage.setStringProperty("doctorName", claim.getDoctorName());
            objectMessage.setStringProperty("doctorType", claim.getDoctorType());

            jmsContext.createProducer().send(queue, objectMessage);

//            String messageSelector = "claimAmount BETWEEN 1001 AND 5000";
//            String messageSelector = "doctorName='John'";
//            String messageSelector = "doctorName LIKE 'J%n'";
//            String messageSelector = "doctorName LIKE 'Joh_'";
//            String messageSelector = "doctorType IN ('neuro','gyna')";
            String messageSelector = "doctorType IN ('neuro','psych') OR JMSPriority BETWEEN 3 AND 6";

            final JMSConsumer consumer = jmsContext.createConsumer(queue, messageSelector);
            final Claim receiveBody = consumer.receiveBody(Claim.class);
            System.out.println(receiveBody);
        }
    }
}
