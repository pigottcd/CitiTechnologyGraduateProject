package com.graduate.training.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;


@Service
public class ActiveMQSender {
    private JmsTemplate jmsTemplate;

    @Autowired
    public ActiveMQSender(JmsTemplate template) {
        this.jmsTemplate = template;
    }
    public void send(String message) {
        jmsTemplate.send("/tbd",
                (Session session)-> session.createTextMessage(message));
    }
}
