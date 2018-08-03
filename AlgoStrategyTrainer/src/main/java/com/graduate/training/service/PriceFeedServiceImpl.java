package com.graduate.training.service;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.*;



/*
    The price feed service is responsible for getting market data
    from an external api, and storing the data of specific tickers
    that are being used by current active strategies.
    
    It counts the number of active strategies following a specific
    ticker, and only stops following the ticker when there are no
    active strategies (tracked through register and deregister).
    
    To get price feed data for a specific ticker, it must first be
    registered.  After registering, there is a "warm up" period for
    the getPriceRange request, because historical data is not immediatly
    pulled on registering but rather accumulated after registering. 
    If you request for a range that is larger than what the price feed 
    currently stores for a specific ticker, the price feed service 
    returns an empty list.
*/
@EnableScheduling
@Service
@Scope(value = "singleton")
public class PriceFeedServiceImpl implements PriceFeedService {

    class PriceListing {
        String ticker;
        int activeCount;
        List<Double> prices;

        PriceListing(String ticker) {
            this.ticker = ticker;
            this.activeCount = 1;
            prices = new ArrayList<>();
        }

        double getCurrentPrice() {
            if(prices.size() == 0) {
                return 0;
            }
            return prices.get(prices.size() -1);
        }
    }
    private static final Logger LOGGER = LogManager.getLogger(PriceFeedServiceImpl.class);
    
    //maps tickers to the pricelisting class for easy access to currently tracked tickers.
    private Map<String, PriceListing> activeListings;

    PriceFeedServiceImpl() {
        activeListings = new HashMap<>();
    }

    //registers a ticker to be tracked by the price feed service.
    //if currently tracked, adds to the active count, if not currently tracked
    //creates a new price listing that will later be updated with prices
    public void register(String ticker) {
        LOGGER.info("Registering ticker: " + ticker);
        PriceListing listing;
        if ((listing = activeListings.get(ticker)) == null) {
            LOGGER.info("New ticker: " + ticker);
            listing = new PriceListing(ticker);
            activeListings.put(listing.ticker, listing);
        }
        else {
            listing.activeCount++;
        }
    }
    public void deregister(String ticker) {
        LOGGER.info("Deregistering ticker: " + ticker);
        PriceListing listing;
        if((listing = activeListings.get(ticker)) == null) {
            LOGGER.warn("deregister, ticker: " + ticker + ", ticker not found");
            return;
        }
        listing.activeCount--;
        if(listing.activeCount <= 0) {
            LOGGER.info("Ticker: " + ticker + " removed from map");
            activeListings.remove(ticker);
        }
    }
    
    public double getCurrentPrice(String ticker) {
        PriceListing listing;
        if ((listing = activeListings.get(ticker)) == null) {
            LOGGER.warn("getCurrentPrice, ticker: " + ticker + ", ticker not found");
            return 0;
        }
        return listing.getCurrentPrice();
    }
    //returns all stored data on a specific ticker
    public List<Double> getPriceRange(String ticker) {
        PriceListing listing;
        if ((listing = activeListings.get(ticker)) == null) {
            LOGGER.warn("getPriceRange, ticker: " + ticker + ", ticker not found");
            return new ArrayList<>();
        }
        return listing.prices;
    }
    //returns the last "range" items of a specific ticker
    //if the # of items requested are not stored, return an empty list
    public List<Double> getPriceRange(String ticker, int range) {
        PriceListing listing;
        if ((listing = activeListings.get(ticker)) == null) {
            LOGGER.warn("getPriceRange, ticker: " + ticker + ", ticker not found");

            return new ArrayList<>();
        }
        if(range > listing.prices.size()) {
            LOGGER.info("getPriceRange, ticker: " + ticker +  "does not have enough current data, exiting");
            return new ArrayList<>();
        }
        return listing.prices.subList(listing.prices.size()-range, listing.prices.size());
    }

    //loops through all of the tracked tickers, and adds a new price to the list of prices
    //for that ticker.  Price is obtianed through an external rest request.
    @Scheduled(fixedDelay = 2000)
    public void updateListings() {
        final String baseUrl = "http://feed.conygre.com:8080/MockYahoo/quotes.csv?s=";
        RestTemplate template = new RestTemplate();

        for (PriceListing listing : activeListings.values()) {
            String price = template.getForObject(baseUrl + listing.ticker + "&f=p0", String.class);
            LOGGER.info("Got price: " + price.trim());
            listing.prices.add(Double.parseDouble(price));
        }
    }

}
