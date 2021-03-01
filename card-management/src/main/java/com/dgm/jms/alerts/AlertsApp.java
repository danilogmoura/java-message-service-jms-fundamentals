package com.dgm.jms.alerts;

import com.dgm.jms.card.model.Card;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AlertsApp {

    public static void main(String[] args) throws NamingException, JMSException {

        final InitialContext initialContext = new InitialContext();
        final Topic topic = (Topic) initialContext.lookup("topic/cardTopic");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            final JMSConsumer sharedConsumer1 = jmsContext.createSharedConsumer(topic, "sharedConsumer");
            final JMSConsumer sharedConsumer2 = jmsContext.createSharedConsumer(topic, "sharedConsumer");

            for (int i = 0; i < 10; i += 2) {
                Message receive1 = sharedConsumer1.receive();
                Card card1 = receive1.getBody(Card.class);
                System.out.println(receive1.getJMSMessageID());
                System.out.println(card1);

                Message receive2 = sharedConsumer2.receive();
                Card card2 = receive2.getBody(Card.class);
                System.out.println(receive2.getJMSMessageID());
                System.out.println(card2);
            }
        }
    }
}
