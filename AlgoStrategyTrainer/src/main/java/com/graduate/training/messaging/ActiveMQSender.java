package com.graduate.training.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Session;


@Component
public class ActiveMQSender {
    private JmsTemplate jmsTemplate;

    @Autowired
    public ActiveMQSender(JmsTemplate template) {
        this.jmsTemplate = template;
    }
    public void send(String message) {
        jmsTemplate.send("queue/testQueue",
                (Session session)-> session.createTextMessage(message));
    }
}
