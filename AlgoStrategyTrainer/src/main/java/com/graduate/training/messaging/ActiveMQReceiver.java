package com.graduate.training.messaging;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQReceiver {

    @JmsListener(destination = "OrderBroker_Reply", containerFactory = "initJmsContainerF")
    public void receive(String message) {
        System.out.println("Got: " + message);
    }
}
