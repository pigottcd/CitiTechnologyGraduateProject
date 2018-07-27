package com.graduate.training.Messaging;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;

import javax.print.attribute.standard.Destination;

//@Service


public class Sender {
    /*
    @Autowired
    private ConnectionFactory factory;
    private JmsTemplate template;

    @PostConstruct
    public void init() {
        template = new JmsTemplate(factory);
    }

    public void send(final Destination dest, final String text) {
        template.send(new MessageCreator() {
            @Override
            public Message createMessage(javax.jms.Session session) throws JMSException {
               return session.createTextMessage(text);
            }
        });
    }
    */
}
