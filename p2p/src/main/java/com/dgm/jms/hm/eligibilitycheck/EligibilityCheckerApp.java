package com.dgm.jms.hm.eligibilitycheck;

import com.dgm.jms.hm.eligibilitycheck.listeners.EligibilityCheckListener;
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
             final JMSContext context = connectionFactory.createContext()) {

            final JMSConsumer consumer = context.createConsumer(requestQueue);
            consumer.setMessageListener(new EligibilityCheckListener());
        }

    }
}
