package com.graduate.training.service;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    private Map<String, PriceListing> activeListings;

    PriceFeedServiceImpl() {
        activeListings = new HashMap<>();
    }


    public void register(String ticker) {
        PriceListing listing;
        if ((listing = activeListings.get(ticker)) == null) {
            listing = new PriceListing(ticker);
            activeListings.put(listing.ticker, listing);
        }
        else {
            listing.activeCount++;
        }
    }
    public void deregister(String ticker) {
        PriceListing listing;
        if((listing = activeListings.get(ticker)) == null) {
            return;
        }
        listing.activeCount--;
        if(listing.activeCount <= 0) {
            activeListings.remove(ticker);
        }
    }
    public double getCurrentPrice(String ticker) {
        PriceListing listing;
        if ((listing = activeListings.get(ticker)) == null) {
            return 0;
        }
        return listing.getCurrentPrice();
    }
    public List<Double> getPriceRange(String ticker) {
        PriceListing listing;
        if ((listing = activeListings.get(ticker)) == null) {
            return new ArrayList<>();
        }
        return listing.prices;
    }
    public List<Double> getPriceRange(String ticker, int range) {
        PriceListing listing;
        if ((listing = activeListings.get(ticker)) == null) {
            return new ArrayList<>();
        }
        System.out.println("range: " + range + ", size: " + listing.prices.size());
        if(range > listing.prices.size()) {
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
            System.out.println("Got price: " + price);
            listing.prices.add(Double.parseDouble(price));
        }
    }

}
