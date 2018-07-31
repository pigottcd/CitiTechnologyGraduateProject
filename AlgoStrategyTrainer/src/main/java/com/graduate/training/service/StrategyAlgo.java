package com.graduate.training.service;


import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;

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
    abstract Order runStrategy(PriceFeedService priceFeed);
}
