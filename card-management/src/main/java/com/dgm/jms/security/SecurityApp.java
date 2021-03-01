package com.dgm.jms.security;

import com.dgm.jms.card.model.Card;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SecurityApp {

    public static void main(String[] args) throws NamingException, InterruptedException, JMSException {

        final InitialContext initialContext = new InitialContext();
        final Topic topic = (Topic) initialContext.lookup("topic/cardTopic");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            jmsContext.setClientID("SecurityApp");
            JMSConsumer consumer = jmsContext.createDurableConsumer(topic, "subscription1");
            consumer.close();

            Thread.sleep(10000);

            consumer = jmsContext.createSharedDurableConsumer(topic, "subscription1");
            final Message receiveMessage = consumer.receive();
            final Card card = receiveMessage.getBody(Card.class);

            System.out.println(card);

            consumer.close();
            jmsContext.unsubscribe("subscription1");
        }
    }
}
