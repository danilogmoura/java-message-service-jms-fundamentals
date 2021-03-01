package com.dgm.jms.card;

import com.dgm.jms.card.model.Card;
import com.github.javafaker.Faker;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CardApp {

    public static void main(String[] args) throws NamingException {

        final InitialContext initialContext = new InitialContext();
        final Topic topic = (Topic) initialContext.lookup("topic/cardTopic");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext()) {

            final JMSProducer producer = jmsContext.createProducer();
            Faker faker = new Faker();

            for (int i = 0; i < 10; i++) {
                Card card = new Card(
                        1 + i,
                        faker.name().firstName(),
                        faker.business().creditCardNumber(),
                        i + i,
                        2022 + i,
                        String.valueOf(222 + i)
                );

                ObjectMessage objectMessage = jmsContext.createObjectMessage(card);
                producer.send(topic, objectMessage);
            }
        }
    }
}
