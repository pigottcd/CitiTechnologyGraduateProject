package com.graduate.training.entities;
import com.graduate.training.messaging.ActiveMQSender;
import com.graduate.training.service.PriceFeedService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class TwoMovingAverages extends Strategy {


    PriceFeedService priceFeed;

    private int shortPeriod = 2;
    private int longPeriod = 10;
    private double longAverage = 0;
    private double shortAverage = 0;

    @Autowired
    public TwoMovingAverages(PriceFeedService feed, int id, String ticker, Integer quantity) {
        super(id, "TwoMovingAverages", ticker, true, quantity);
        this.priceFeed = feed;
    }

    public void runStrategy(ActiveMQSender sender) {
        //initially check the status of your position
        //if you hit the P/L threshold
            //  close out position
            //  set active status to false
            //  deregister

        String ticker = getTicker();
        priceFeed.register(ticker);


        List<Double> currentShortRange = priceFeed.getPriceRange(ticker, shortPeriod);
        List<Double> currentLongRange = priceFeed.getPriceRange(ticker, longPeriod);
        if (currentLongRange.size() < longPeriod) {
            System.out.println("warming up, currentLongRangeSize(): " + currentLongRange.size());
            return;
        }

        //get Averages
        double currentShortAverage = getAverage(currentShortRange);
        double currentLongAverage = getAverage(currentLongRange);
        System.out.println("shortAverage: " + currentShortAverage + ", longAverage: " + currentLongAverage);

        double price = priceFeed.getCurrentPrice(ticker);
        if ((shortAverage > longAverage)&&(currentShortAverage < currentLongAverage)){
            Order newSellOrder = new Order(false, 1, price,100, ticker, LocalDateTime.now());
            System.out.println("New Sell Order:" + newSellOrder.toString());
            sender.send(newSellOrder.toString());
        }


        if ((longAverage > shortAverage)&&(currentLongAverage < currentShortAverage)) {
            Order newBuyOrder = new Order(true, 2, price, 100, ticker, LocalDateTime.now());
            System.out.println("New Buy Order:" + newBuyOrder.toString());
            sender.send(newBuyOrder.toString());
        }
        
        shortAverage = currentShortAverage;
        longAverage = currentLongAverage;
    }

    public double getAverage (List<Double> range){
        if (range.size() == 0) {
            return 0;
        }
        double sum = 0.0;
        for (double price : range){
            sum += price;
        }
        return sum/range.size();
    }
}
