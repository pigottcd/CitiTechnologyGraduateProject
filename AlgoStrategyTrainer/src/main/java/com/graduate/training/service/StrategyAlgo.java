package com.graduate.training.service;


import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;

import java.time.LocalDateTime;
import java.util.List;

public abstract class StrategyAlgo {

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
        System.out.println("TOTAL QUANTITY: " + totalQuantity);
        totalOutstanding -= totalQuantity * price;
        System.out.println("Total Outstanding: " + totalOutstanding + ", Limit: " +
                strategy.getPAndL() * strategy.getQuantity() * orders.get(0).getPrice());
        if (Math.abs(totalOutstanding) >
                Math.abs(strategy.getPAndL() * strategy.getQuantity() * orders.get(0).getPrice())) {
            if (totalQuantity > 0) {
                return new Order(false, price, strategy.getQuantity(), getTicker(), LocalDateTime.now(), getId());
            }
            return new Order(true, price, strategy.getQuantity(), getTicker(), LocalDateTime.now(), getId());
        }
        return null;
    }

}
