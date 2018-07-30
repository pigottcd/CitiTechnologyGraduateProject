package com.graduate.training.entities;
import com.graduate.training.messaging.ActiveMQSender;
import com.graduate.training.service.PriceFeedService;
import com.graduate.training.quantitative.MovingAverage;
import com.graduate.training.service.PriceFeedServiceImpl;
import com.graduate.training.service.PriceFeedService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class TwoMovingAverages extends Strategy {

    @Autowired
    PriceFeedService priceFeed;
    private int shortPeriod = 2;
    private int longPeriod = 10;
    private double longAverage;
    private double shortAverage;
    public void runStrategy(ActiveMQSender sender) {
        //initially check the status of your position
        //if you hit the P/L threshold
            //  close out position
            //  set active status to false
            //  deregister

        String ticker = getTicker();
        priceFeed.register(ticker);
        double price = priceFeed.getCurrentPrice(ticker);

        List<Double> currentShortRange = priceFeed.getPriceRange(ticker, shortPeriod);
        List<Double> currentLongRange = priceFeed.getPriceRange(ticker, longPeriod);
        if (currentLongRange.size() < longPeriod) {return;}

        //get Averages
        double currentShortAverage = getSum(currentShortRange);
        double currentLongAverage = getSum(currentLongRange);

        //Option 1
        //if range size < 10 then return;

        //Option 2
        if ((shortAverage > longAverage)&&(currentShortAverage < currentLongAverage)){
            Order newSellOrder = new Order(false, 1, price,100, ticker, LocalDateTime.now());
            sender.send(newSellOrder.toString());
        }

        //Option 3
        if ((longAverage > shortAverage)&&(currentLongAverage < currentShortAverage)) {
            Order newBuyOrder = new Order(true, 2, price, 100, ticker, LocalDateTime.now());
            sender.send(newBuyOrder.toString());
        }
        
        shortAverage = currentShortAverage;
        longAverage = currentLongAverage;
    }

    public double getSum (List<Double> range){
        double sum = 0.0;
        for (double price : range){
            sum += price;
        }
        return sum;
    }
}
