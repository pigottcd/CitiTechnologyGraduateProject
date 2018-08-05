package com.graduate.training.service;

import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;

public class BollingerBandsAlgo extends StrategyAlgo {

    private int windowLength;
    private int deviation;
    private double movingAverage;
    private Boolean pastBuy;

    public BollingerBandsAlgo(Strategy strategy, int windowLength, int deviation) {
        super(strategy);
        this.windowLength = windowLength;
        this.deviation = deviation;
    }

    public BollingerBandsAlgo(){

    }

    public Order runStrategy(PriceFeedService priceFeed){
       return null;
    }

    public int getWindowLength() {
        return windowLength;
    }

    public void setWindowLength(int windowLength) {
        this.windowLength = windowLength;
    }

    public int getDeviation() {
        return deviation;
    }

    public void setDeviation(int deviation) {
        this.deviation = deviation;
    }

    public double getMovingAverage() {
        return movingAverage;
    }

    public void setMovingAverage(double movingAverage) {
        this.movingAverage = movingAverage;
    }

    public Boolean getPastBuy() {
        return pastBuy;
    }

    public void setPastBuy(Boolean pastBuy) {
        this.pastBuy = pastBuy;
    }
}
