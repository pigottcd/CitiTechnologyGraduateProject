package com.graduate.training.service;

import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;


import java.time.LocalDateTime;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class TwoMovingAveragesAlgo extends StrategyAlgo {


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


    Order runStrategy(PriceFeedService priceFeed) {
        Order newOrder = null;
        String ticker = getTicker();

        List<Double> currentShortRange = priceFeed.getPriceRange(ticker, shortPeriod);
        List<Double> currentLongRange = priceFeed.getPriceRange(ticker, longPeriod);
        if (currentLongRange.size() < longPeriod) {
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
    }
}
