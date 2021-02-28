package com.dgm.jms.messagestructure;

import com.dgm.jms.model.Patient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;

public class MessageTypesDemo {

    public static void main(String[] args) throws Exception {

        InitialContext initialContext = new InitialContext();
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");

        try (ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
             JMSContext jmsContext = connectionFactory.createContext()) {

            JMSProducer producer = jmsContext.createProducer();
            producer.setDeliveryDelay(300);

            TextMessage textMessage = jmsContext.createTextMessage("Arise Awake and stop not till the goal is reached");
//            producer.send(queue, textMessage);

            BytesMessage bytesMessage = jmsContext.createBytesMessage();
            bytesMessage.writeUTF("Danilo");
            bytesMessage.writeLong(123l);
//            producer.send(queue, bytesMessage);

            StreamMessage streamMessage = jmsContext.createStreamMessage();
            streamMessage.writeBoolean(true);
            streamMessage.writeFloat(2.5f);
//            producer.send(queue, streamMessage);

            MapMessage mapMessage = jmsContext.createMapMessage();
            mapMessage.setBoolean("isCreditAvailable", true);
            producer.send(queue, mapMessage);

            ObjectMessage objectMessage = jmsContext.createObjectMessage();
            Patient patient = new Patient();
            patient.setId(1);
            patient.setName("Danilo");
            objectMessage.setObject(patient);
            producer.send(queue, objectMessage);

//            Message messageReceived = jmsContext.createConsumer(queue).receive(500);
//            System.out.println(messageReceived);

//            BytesMessage messageReceived = (BytesMessage) jmsContext.createConsumer(queue).receive();
//            System.out.println(messageReceived.readUTF());
//            System.out.println(messageReceived.readLong());

//            StreamMessage messageReceived = (StreamMessage) jmsContext.createConsumer(queue).receive();
//            System.out.println(messageReceived.readBoolean());
//            System.out.println(messageReceived.readFloat());

//            MapMessage messageReceived = (MapMessage) jmsContext.createConsumer(queue).receive();
//            System.out.printf("isCreditAvailable: %b\n", messageReceived.getBoolean("isCreditAvailable"));

            ObjectMessage messageReceived = (ObjectMessage) jmsContext.createConsumer(queue).receive();
            Patient patientReceived = (Patient) messageReceived.getObject();
            System.out.println(patientReceived.getId());
            System.out.println(patientReceived.getName());
        }
    }
}
