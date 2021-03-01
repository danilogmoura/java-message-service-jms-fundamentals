package com.dgm.jms.grouping;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Map;

public class MessageGroupingDemo {

    public static void main(String[] args) throws NamingException, JMSException {

        final InitialContext initialContext = new InitialContext();
        final Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             final JMSContext jmsContext = connectionFactory.createContext();
             final JMSContext jmsContext2 = connectionFactory.createContext()) {

            final JMSProducer producer = jmsContext.createProducer();

            int count = 10;
            TextMessage[] messages = new TextMessage[count];

            for (int i = 0; i < count; i++) {
                messages[i] = jmsContext.createTextMessage("Group-0 message" + i);
                messages[i].setStringProperty("JMSXGroupID", "Group-0");
                producer.send(queue, messages[i]);
            }

            final JMSConsumer consumer = jmsContext2.createConsumer(queue);

        }
    }

    class MyListener implements MessageListener {

        private final String name;
        private final Map<String, String> receivedMessages;

        public MyListener(String name, Map<String, String> receivedMessages) {
            this.name = name;
            this.receivedMessages = receivedMessages;
        }

        @Override
        public void onMessage(Message message) {
            TextMessage textMessage = (TextMessage) message;

            try {
                System.out.println("Message Received is " + textMessage.getText());
                System.out.println("Listener Name " + name);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
