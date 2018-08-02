package com.graduate.training.service;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.*;

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
    private Map<String, PriceListing> activeListings;

    PriceFeedServiceImpl() {
        activeListings = new HashMap<>();
    }


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
    public List<Double> getPriceRange(String ticker) {
        PriceListing listing;
        if ((listing = activeListings.get(ticker)) == null) {
            LOGGER.warn("getPriceRange, ticker: " + ticker + ", ticker not found");
            return new ArrayList<>();
        }
        return listing.prices;
    }
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
