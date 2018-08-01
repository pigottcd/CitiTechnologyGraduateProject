package com.graduate.training.service;


import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;

import java.util.List;

public abstract class StrategyAlgo {

    private Strategy strategy;


    public StrategyAlgo(Strategy strategy) {
        this.strategy = strategy;
    }
    public int getId() {
        return strategy.getId();
    }
    public String getTicker() {
        return strategy.getTicker();
    }
    public double getPAndL() {
        return strategy.getPAndL();
    }
    public int getQuantity() {
        return strategy.getQuantity();
    }
    abstract Order calculateExit(double currentPrice, List<Order> orders);
    abstract Order runStrategy(PriceFeedService priceFeed);
}
