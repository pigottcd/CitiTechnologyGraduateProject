package com.graduate.training.messaging;

import com.graduate.training.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;


@Component
public class ActiveMQSender {
    private JmsTemplate jmsTemplate;
    @Autowired
    public ActiveMQSender(JmsTemplate template) {
        this.jmsTemplate = template;
    }
    public void send(Order message) {
        jmsTemplate.send("OrderBroker",
                (Session session)-> {
                    Message m = session.createTextMessage(message.toString());
                    m.setJMSCorrelationID(UUID.randomUUID().toString());
                    return m;
                });
    }
}
