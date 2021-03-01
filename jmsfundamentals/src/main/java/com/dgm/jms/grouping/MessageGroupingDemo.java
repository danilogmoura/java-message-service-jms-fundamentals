package com.dgm.jms.grouping;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.lang.IllegalStateException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageGroupingDemo {

    public static void main(String[] args) throws NamingException, JMSException {

        final InitialContext initialContext = new InitialContext();
        final Queue queue = (Queue) initialContext.lookup("queue/myQueue");
        Map<String, String> receivedMessages = new ConcurrentHashMap<>();

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

            final JMSConsumer consumer1 = jmsContext2.createConsumer(queue);
            consumer1.setMessageListener(new MyListener("Consumer-1", receivedMessages));

            final JMSConsumer consumer2 = jmsContext2.createConsumer(queue);
            consumer2.setMessageListener(new MyListener("Consumer-2", receivedMessages));

            for (TextMessage message : messages) {
                if (!receivedMessages.get(message.getText()).equals("Consumer-1")) {
                    throw new IllegalStateException("Group Message " + message.getText() + " has gone to the wrong receiver");
                }
            }
        }
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

            receivedMessages.put(textMessage.getText(), name);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
