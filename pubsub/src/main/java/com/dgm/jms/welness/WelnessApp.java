package com.dgm.jms.welness;

import com.dgm.jms.hr.model.Employee;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class WelnessApp {

    public static void main(String[] args) throws NamingException, JMSException {

        final InitialContext initialContext = new InitialContext();
        final Topic topic = (Topic) initialContext.lookup("topic/empTopic");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            final JMSConsumer consumer1 = jmsContext.createSharedConsumer(topic, "sharedConsumer");
            final JMSConsumer consumer2 = jmsContext.createSharedConsumer(topic, "sharedConsumer");

            for (int i = 0; i < 10; i += 2) {
                final Message receive1 = consumer1.receive();
                final Employee employee1 = receive1.getBody(Employee.class);
                System.out.println(receive1.getJMSMessageID());
                System.out.println("Consumer One: " + employee1);

                final Message receive2 = consumer2.receive();
                final Employee employee2 = receive2.getBody(Employee.class);
                System.out.println(receive2.getJMSMessageID());
                System.out.println("Consumer Two: " + employee2);
            }
        }
    }
}
