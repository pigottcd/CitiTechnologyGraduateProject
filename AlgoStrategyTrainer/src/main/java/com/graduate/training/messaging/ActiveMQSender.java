package com.graduate.training.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;


@Component
public class ActiveMQSender {
    private JmsTemplate jmsTemplate;

    @Autowired
    public ActiveMQSender(JmsTemplate template) {
        this.jmsTemplate = template;
    }
    public void send(String message) {
        jmsTemplate.send("OrderBroker",
                (Session session)-> {
                    Message m = session.createTextMessage(message);
                    m.setJMSCorrelationID(UUID.randomUUID().toString());
                    return m;
                });
    }
}
