package com.dgm.jms.security;

import com.dgm.jms.hr.model.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SecurityApp {

    public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

        final InitialContext initialContext = new InitialContext();
        final Topic topic = (Topic) initialContext.lookup("topic/empTopic");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            jmsContext.setClientID("securityApp");
            JMSConsumer consumer = jmsContext.createDurableConsumer(topic, "subscription1");

            //simulating service down
            consumer.close();
            System.out.println("service down...");

            Thread.sleep(10000);

            consumer = jmsContext.createDurableConsumer(topic, "subscription1");
            final Message message = consumer.receive();
            final Employee employee = message.getBody(Employee.class);
            System.out.println(employee);

            consumer.close();
            jmsContext.unsubscribe("subscription1");
        }
    }
}
