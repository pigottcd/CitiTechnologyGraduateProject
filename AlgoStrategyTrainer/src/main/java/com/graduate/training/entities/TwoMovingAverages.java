package com.graduate.training.entities;

import com.graduate.training.messaging.ActiveMQSender;

import java.time.LocalDateTime;

public class TwoMovingAverages extends Strategy {

    public void runStrategy(ActiveMQSender sender) {
        //tbd
        Order newOrder = new Order(true, 1, 10, 100, "GE", LocalDateTime.now());
        sender.send(newOrder.toString());
    }
}
