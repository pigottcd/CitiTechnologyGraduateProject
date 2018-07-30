package com.graduate.training.service;

import com.graduate.training.messaging.ActiveMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StrategyServiceImpl implements StrategyService {

    @Autowired
    ActiveMQSender sender;

    @Scheduled(fixedDelay = 2000)
    public void runStrategies() {

        //db query for active strats
        //run all active strats
        //send any generated trades
    }
}
