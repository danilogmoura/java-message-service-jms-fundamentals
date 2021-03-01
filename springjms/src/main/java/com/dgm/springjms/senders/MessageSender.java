package com.dgm.springjms.senders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private JmsTemplate jmsTemplate;

    @Value("${spring.jms.myQueue}")
    private String queue;

    public void send(String message) {
//        jmsTemplate.convertAndSend(queue, message);
        MessageCreator messageCreator = s -> s.createTextMessage("Hello Spring JMS!!");
        jmsTemplate.send(queue, messageCreator);
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}
