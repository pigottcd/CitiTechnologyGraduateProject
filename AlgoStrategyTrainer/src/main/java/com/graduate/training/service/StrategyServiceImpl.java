package com.graduate.training.service;

import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;
import com.graduate.training.messaging.ActiveMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Service
public class StrategyServiceImpl implements StrategyService {

    private PriceFeedService feed;
    private ActiveMQSender sender;
    private List<StrategyAlgo> strategies;

    @PersistenceContext
    private EntityManager em;
    @Autowired
    public StrategyServiceImpl(ActiveMQSender sender, PriceFeedService feed) {
        this.feed = feed;
        this.sender = sender;
        strategies = new ArrayList<>();
    }

    public void addStrategy(StrategyAlgo s) {
        strategies.add(s);
    }

    @Scheduled(fixedDelay = 2000)
    @Transactional(propagation = Propagation.REQUIRED)
    public void runStrategies() {

        //db query for active strats
        //run all active strats
        //send any generated trades
        for(StrategyAlgo s : strategies) {
            System.out.println("Running strat: " + s.getId());
            Order newOrder = s.runStrategy(feed);
            if(newOrder != null) {
                sender.send(newOrder.toString());
            }
        }
    }
}
