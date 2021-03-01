package com.dgm.jms.hr;

import com.dgm.jms.hr.model.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class HRApp {

    public static void main(String[] args) throws NamingException {

        final InitialContext initialContext = new InitialContext();
        final Topic topic = (Topic) initialContext.lookup("topic/empTopic");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            final Employee employee = new Employee(
                    123,
                    "Danilo",
                    "Moura",
                    "danilogmoura@gmail.com",
                    "trainee",
                    "+55 (19) 99999-99-89"
            );

            final ObjectMessage objectMessage = jmsContext.createObjectMessage(employee);
            for (int i = 0; i < 10; i++) {
                jmsContext.createProducer().send(topic, objectMessage);
            }

            System.out.println("Message Sent");
        }
    }
}
