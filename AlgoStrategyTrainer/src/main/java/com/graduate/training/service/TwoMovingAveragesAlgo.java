package com.graduate.training.service;

import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;


import java.time.LocalDateTime;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;

/*
    Implementation of a two moving averages strategy.
    Calculates a short and long average of historical price data.
    A buy or sell is triggered when the short average's value
    swaps with the long average's value.
 */
public class TwoMovingAveragesAlgo extends StrategyAlgo {

    private static final Logger LOGGER = LogManager.getLogger(TwoMovingAveragesAlgo.class);
    private int shortPeriod;
    private int longPeriod;
    private double longAverage = 0;
    private double shortAverage = 0;
    private Boolean pastBuy = null;

    public TwoMovingAveragesAlgo(Strategy strategy, int shortPeriod, int longPeriod) {
        super(strategy);
        this.shortPeriod = shortPeriod;
        this.longPeriod = longPeriod;
    }

    TwoMovingAveragesAlgo () {
    }


    public Order runStrategy(PriceFeedService priceFeed) {
        Order newOrder = null;
        String ticker = getTicker();

        List<Double> currentShortRange = priceFeed.getPriceRange(ticker, shortPeriod);
        List<Double> currentLongRange = priceFeed.getPriceRange(ticker, longPeriod);
        if(shortPeriod >= longPeriod ){
            LOGGER.error("short period set to longer than long period, exiting");
            return null;
        }

        if (currentLongRange.size() < longPeriod) {
            LOGGER.info("warming up, currentLongRangeSize(): " + currentLongRange.size());
            return null;
        }
        //get Averages
        double currentShortAverage = getAverage(currentShortRange);
        double currentLongAverage = getAverage(currentLongRange);
        LOGGER.info("shortAverage: " + currentShortAverage + ", longAverage: " + currentLongAverage);

        double price = priceFeed.getCurrentPrice(ticker);
        if ((shortAverage > longAverage)&&(currentShortAverage < currentLongAverage)){
            if (pastBuy != null && !pastBuy) {
                return null;
            }
            pastBuy = false;
            newOrder = new Order(false, price,getStrategy().getQuantity(), ticker, LocalDateTime.now(), getId());
            LOGGER.info("New Sell Order:" + newOrder.toString());
        }
        else if ((longAverage > shortAverage)&&(currentLongAverage < currentShortAverage)) {
            if (pastBuy != null && pastBuy) {
                return null;
            }
            pastBuy = true;
            newOrder = new Order(true, price, getStrategy().getQuantity(), ticker, LocalDateTime.now(), getId());
            LOGGER.info("New Buy Order:" + newOrder.toString());
        }

        shortAverage = currentShortAverage;
        longAverage = currentLongAverage;
        return newOrder;
    }

    public int getShortPeriod() {
        return shortPeriod;
    }

    public void setShortPeriod(int shortPeriod) {
        this.shortPeriod = shortPeriod;
    }

    public int getLongPeriod() {
        return longPeriod;
    }

    public void setLongPeriod(int longPeriod) {
        this.longPeriod = longPeriod;
    }

    public double getLongAverage() {
        return longAverage;
    }

    public void setLongAverage(double longAverage) {
        this.longAverage = longAverage;
    }

    public double getShortAverage() {
        return shortAverage;
    }

    public void setShortAverage(double shortAverage) {
        this.shortAverage = shortAverage;
    }

    public Boolean getPastBuy() {
        return pastBuy;
    }

    public void setPastBuy(Boolean pastBuy) {
        this.pastBuy = pastBuy;
    }
}
