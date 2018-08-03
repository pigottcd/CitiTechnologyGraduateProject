package com.graduate.training.service;


import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.time.LocalDateTime;
import java.util.List;

/*
    The Strategy Algo is responsible for running algorithms based on
    the information provided by the strategy class.  It takes in a 
    strategy in its constructor and will run the specified strategy
    in the runStrategy method.  The other main method is the
    calculate exit function which determines when the algorithm should
    stop running based on a P&L calculation.
*/
public abstract class StrategyAlgo {

    private static final Logger LOGGER = LogManager.getLogger(StrategyAlgo.class);
    private Strategy strategy;


    StrategyAlgo(Strategy strategy) {
        this.strategy = strategy;
    }
    int getId() {
        return strategy.getId();
    }
    String getTicker() {
        return strategy.getTicker();
    }
    void setActive(boolean active) { strategy.setActive(active); }
    abstract Order runStrategy(PriceFeedService priceFeed);
    Strategy getStrategy() {
        return strategy;
    }
    double getAverage (List<Double> range){
        if (range.size() == 0) {
            return 0;
        }
        double sum = 0.0;
        for (double price : range){
            sum += price;
        }
        return sum/range.size();
    }
    Order calculateExit(double price, List<Order> orders) {
        if (orders.size() == 0) {
            return null;
        }
        int totalQuantity = 0;
        double totalOutstanding = 0;
        //calculate profit and loss based on past trades
        for (Order o : orders) {
            if (o.isBuy()) {
                totalQuantity += o.getSize();
                totalOutstanding += o.getSize() * o.getPrice();
            }
            else {
                totalQuantity -= o.getSize();
                totalOutstanding -= o.getSize() * o.getPrice();
            }
        }
        //calculate value of current position
        totalOutstanding -= totalQuantity * price;
        //if range is outside of acceptable p&l boundry
        if (Math.abs(totalOutstanding) >
                Math.abs(strategy.getPAndL() * strategy.getQuantity() * orders.get(0).getPrice())) {

            LOGGER.info("Exit calculated wth total outstanding: " + totalOutstanding
                    + "and P and L: " + strategy.getPAndL());

            if (totalQuantity > 0) {
                return new Order(false, price, strategy.getQuantity(), getTicker(), LocalDateTime.now(), getId());
            }
            return new Order(true, price, strategy.getQuantity(), getTicker(), LocalDateTime.now(), getId());
        }
        return null;
    }

}
