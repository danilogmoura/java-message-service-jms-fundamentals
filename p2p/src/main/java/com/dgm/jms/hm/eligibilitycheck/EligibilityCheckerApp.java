package com.dgm.jms.hm.eligibilitycheck;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EligibilityCheckerApp {

    public static void main(String[] args) throws NamingException {

        final InitialContext initialContext = new InitialContext();
        final Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            final JMSConsumer consumer1 = jmsContext.createConsumer(requestQueue);
            final JMSConsumer consumer2 = jmsContext.createConsumer(requestQueue);

            for (int i = 0; i < 10; i += 2) {
                System.out.printf("ConsumerOne: %s\n", consumer1.receive());
                System.out.printf("ConsumerTwo: %s\n", consumer2.receive());
            }
//            consumer.setMessageListener(new EligibilityCheckListener());

//            Thread.sleep(10000);
        }
    }
}
