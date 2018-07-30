package com.graduate.training.service;

import com.graduate.training.entities.Strategy;
import com.graduate.training.messaging.ActiveMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Service
public class StrategyServiceImpl implements StrategyService {


    private ActiveMQSender sender;
    private List<Strategy> strategies;

    @Autowired
    public StrategyServiceImpl(ActiveMQSender sender) {
        this.sender = sender;
        strategies = new ArrayList<>();
    }

    public void addStrategy(Strategy s) {
        strategies.add(s);
    }

    @Scheduled(fixedDelay = 2000)
    public void runStrategies() {

        //db query for active strats
        //run all active strats
        //send any generated trades
        for(Strategy s : strategies) {
            System.out.println("Running strat");
            s.runStrategy(sender);
        }
    }
}
