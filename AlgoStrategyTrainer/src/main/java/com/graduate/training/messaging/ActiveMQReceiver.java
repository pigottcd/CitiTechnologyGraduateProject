package com.graduate.training.messaging;


import com.graduate.training.entities.Order;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Component
public class ActiveMQReceiver {
    private static final Logger LOGGER = LogManager.getLogger(ActiveMQReceiver.class);

    @JmsListener(destination = "OrderBroker_Reply", containerFactory = "initJmsContainerF")
    public void receive(String message) {
        LOGGER.info("Got new message: " + message);
        try {
            Order o = new Order(message);
            LOGGER.info(o.toString());
        } catch (Exception e) {
            LOGGER.error("Failed to receive message: " + e.getMessage());
        }
    }
}
