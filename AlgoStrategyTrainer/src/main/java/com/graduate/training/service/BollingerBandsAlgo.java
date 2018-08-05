package com.graduate.training.service;

import com.graduate.training.entities.Order;
import com.graduate.training.entities.Strategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.List;

public class BollingerBandsAlgo extends StrategyAlgo {

    private static final Logger LOGGER = LogManager.getLogger(BollingerBandsAlgo.class);

    private int windowLength;
    private double deviation;
    private double movingAverage;
    private Boolean pastBuy;

    public BollingerBandsAlgo(Strategy strategy, int windowLength, double deviation) {
        super(strategy);
        this.windowLength = windowLength;
        this.deviation = deviation;
    }

    public BollingerBandsAlgo(){

    }

    public Order runStrategy(PriceFeedService priceFeed){
        Order newOrder = null;
        String ticker = getTicker();

        List<Double> currentRange = priceFeed.getPriceRange(ticker, windowLength);
        if (currentRange.size() < windowLength) {
            LOGGER.info("warming up, currentRange.Size(): " + currentRange.size());
            return newOrder;
        }

        //get Averages
        double currentAverage = getAverage(currentRange);
        double stdev = calculateStdev(currentRange, currentAverage);

        double price = priceFeed.getCurrentPrice(ticker);
        LOGGER.info("Price: " + price + ", stdev: " + stdev + ", limit: " +(stdev*deviation + currentAverage));
        if (price > stdev * deviation + currentAverage){
            if (pastBuy != null && !pastBuy) {
                return newOrder;
            }
            pastBuy = false;
            newOrder = new Order(false, price,getStrategy().getQuantity(), ticker, LocalDateTime.now(), getId());
            LOGGER.info("New Sell Order:" + newOrder.toString());
        }
        else if (price <  currentAverage - stdev*deviation) {

            if (pastBuy != null && pastBuy) {
                return newOrder;
            }
            pastBuy = true;
            newOrder = new Order(true, price, getStrategy().getQuantity(), ticker, LocalDateTime.now(), getId());
            LOGGER.info("New Buy Order:" + newOrder.toString());
        }

        return newOrder;
    }

    public int getWindowLength() {
        return windowLength;
    }

    public void setWindowLength(int windowLength) {
        this.windowLength = windowLength;
    }

    public double getDeviation() {
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

    private double calculateStdev(List<Double> range, double average) {
        double total = 0;
        for (double price : range) {
            total += Math.pow((price-average), 2);
        }
        total /= range.size();
        return Math.sqrt(total);
    }
}
