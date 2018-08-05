package com.graduate.training.service;

import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;

import java.time.LocalDateTime;
import java.util.List;

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
        Order newOrder = null;
        String ticker = getTicker();

        List<Double> currentRange = priceFeed.getPriceRange(ticker, shortPeriod);
        if (currentRange.size() < longPeriod) {
            System.out.println("warming up, currentLongRangeSize(): " + currentLongRange.size());
            return newOrder;
        }

        //get Averages
        double currentShortAverage = getAverage(currentShortRange);
        double currentLongAverage = getAverage(currentLongRange);
        System.out.println("shortAverage: " + currentShortAverage + ", longAverage: " + currentLongAverage);

        double price = priceFeed.getCurrentPrice(ticker);
        if ((shortAverage > longAverage)&&(currentShortAverage < currentLongAverage)){
            if (pastBuy == null) {
                pastBuy = false;
            } else if (!pastBuy) {
                return newOrder;
            }
            pastBuy = false;
            newOrder = new Order(false, price,getStrategy().getQuantity(), ticker, LocalDateTime.now(), getId());
            System.out.println("New Sell Order:" + newOrder.toString());
        }
        else if ((longAverage > shortAverage)&&(currentLongAverage < currentShortAverage)) {
            if (pastBuy == null) {
                pastBuy = true;
            } else if (pastBuy) {
                return newOrder;
            }
            pastBuy = true;
            newOrder = new Order(true, price, getStrategy().getQuantity(), ticker, LocalDateTime.now(), getId());
            System.out.println("New Buy Order:" + newOrder.toString());
        }

        shortAverage = currentShortAverage;
        longAverage = currentLongAverage;
        return newOrder;
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
