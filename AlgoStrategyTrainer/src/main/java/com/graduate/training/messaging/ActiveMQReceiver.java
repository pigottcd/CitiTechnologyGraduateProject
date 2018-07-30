package com.graduate.training.messaging;


import com.graduate.training.entities.Order;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ActiveMQReceiver {

    @PersistenceContext
    private EntityManager em;
    @JmsListener(destination = "OrderBroker_Reply", containerFactory = "initJmsContainerF")
    @Transactional
    public void receive(String message) {

        System.out.println("Got: " + message);
        try {

            Order o = new Order(message);
            System.out.println(o.toString());
            em.merge(o);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
