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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@EnableScheduling
@Service
public class StrategyServiceImpl implements StrategyService {
    private static final Logger LOGGER = LogManager.getLogger(StrategyServiceImpl.class);

    private PriceFeedService feed;
    private OrderService orderService;
    private List<StrategyAlgo> strategies;
    private StrategyRepository dao;
    private boolean startup;
    @Autowired
    public StrategyServiceImpl(OrderService orderService, PriceFeedService feed, StrategyRepository dao) {
        startup = true;
        //SimpleLayout  layout
        this.feed = feed;
        this.orderService = orderService;
        this.dao = dao;
        strategies = new ArrayList<>();
        for(Strategy s : dao.findByActiveIsTrue()) {
            addStrategy(s);
        }
        startup = false;
    }

    public void addStrategy(Strategy s) {
        LOGGER.info("Adding Strategy: " +s.getId());
        StrategyAlgo algo = null;
        switch(s.getType()) {
            case "TwoMovingAverages":
                algo = new TwoMovingAveragesAlgo(s,s.getShortPeriod(), s.getLongPeriod());
        }
        if (algo == null) {
            LOGGER.error("Expected valid strategy type got: " + s.getType());
            return;
        }
        feed.register(s.getTicker());
        strategies.add(algo);
        dao.save(s);
    }
    public void deactivateStrategy(Integer id){
        Iterator<StrategyAlgo> i = strategies.iterator();
        while (i.hasNext()) {
            StrategyAlgo s = i.next();
            if(s.getId() == id){
                LOGGER.info("Deactivating strategy: " + s.getId());
                feed.deregister(s.getTicker());
                s.setActive(false);
                dao.save(s.getStrategy());
                i.remove();
                return;
            }
        }
        LOGGER.warn("Deactivating strategy: " + id + " not found");
    }

    public Iterable<Strategy> getStrategies(){
        return dao.findAll();
    }

    public List<Strategy> getActiveStrategies(){
        return dao.findByActiveIsTrue();
    }

    public Strategy getStrategyById(Integer id) { return dao.findById(id);}

    //Charles's Deactivate Strategy

    @Scheduled(fixedDelay = 2000)
    @Transactional(propagation = Propagation.REQUIRED)
    public void runStrategies() {
        if(startup)
            return;
        //db query for active strats
        //run all active strats
        //send any generated trades
        Iterator<StrategyAlgo> i = strategies.iterator();
        List<Integer> removeIds = new ArrayList<>();
        while (i.hasNext()) {
            StrategyAlgo s = i.next();
            LOGGER.info("Running strat: " + s.getId());
            //get all orders per strat id from dao
            Order exitingOrder = s.calculateExit(
                    feed.getCurrentPrice(s.getTicker()), orderService.getOrderByStrategyID(s.getId())
            );
            if(exitingOrder != null) {
                LOGGER.info("Exiting strategy " + s.getId() +  " with order " + exitingOrder.getId());
                orderService.addOrder(exitingOrder);
                removeIds.add(s.getId());
                continue;
            }
            //pass all orders into a new stratalgo function "calculateExit"
            Order newOrder = s.runStrategy(feed);
            if(newOrder != null) {
                orderService.addOrder(newOrder);
            }

        }
        for(int idToRemove : removeIds) {
            deactivateStrategy(idToRemove);
        }
    }
}
