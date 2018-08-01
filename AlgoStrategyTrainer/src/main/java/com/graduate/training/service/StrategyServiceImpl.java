package com.graduate.training.service;

import com.graduate.training.dao.StrategyRepository;
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
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@EnableScheduling
@Service
public class StrategyServiceImpl implements StrategyService {

    private PriceFeedService feed;
    private OrderService orderService;
    private List<StrategyAlgo> strategies;
    private StrategyRepository dao;

    @Autowired
    public StrategyServiceImpl(OrderService orderService, PriceFeedService feed, StrategyRepository dao) {
        this.feed = feed;
        this.orderService = orderService;
        this.dao = dao;
        strategies = new ArrayList<>();
    }

    public void addStrategy(Strategy s) {
        StrategyAlgo algo = null;
        switch(s.getType()) {
            case "TwoMovingAverages":
                algo = new TwoMovingAveragesAlgo(s,s.getShortPeriod(), s.getLongPeriod());
        }
        if (algo == null) {
            System.out.println("ERROR: expected valid strategy type got: " + s.getType());
            return;
        }
        feed.register(s.getTicker());
        strategies.add(algo);
        dao.save(s);
    }

    @Scheduled(fixedDelay = 2000)
    @Transactional(propagation = Propagation.REQUIRED)
    public void runStrategies() {

        //db query for active strats
        //run all active strats
        //send any generated trades
        Iterator<StrategyAlgo> i = strategies.iterator();
        while (i.hasNext()) {
            StrategyAlgo s = i.next();
            System.out.println("Running strat: " + s.getId());
            //get all orders per strat id from dao
            Order exitingOrder = s.calculateExit(
                    feed.getCurrentPrice(s.getTicker()), orderService.getOrderByStrategyID(s.getId())
            );
            if(exitingOrder != null) {
                orderService.addOrder(exitingOrder);
                feed.deregister(s.getTicker());
                System.out.println("Exiting Strategy " + s.getId());
                i.remove();
                continue;
            }
            //pass all orders into a new stratalgo function "calculateExit"
            Order newOrder = s.runStrategy(feed);
            if(newOrder != null) {
                orderService.addOrder(newOrder);
            }

        }
    }
}
