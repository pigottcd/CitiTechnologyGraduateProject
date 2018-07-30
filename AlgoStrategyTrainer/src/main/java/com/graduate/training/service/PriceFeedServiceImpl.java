package com.graduate.training.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
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
        if(listing.activeCount >= 0) {
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
        return listing.prices.subList(listing.prices.size()-(range +1), listing.prices.size() -1);
    }

    @Scheduled(fixedDelay = 2000)
    public void updateListings() {
        final String baseUrl = "http://feed.conygre.com:8080/MockYahoo/quotes.csv?s=";
        RestTemplate template = new RestTemplate();

        for (PriceListing listing : activeListings.values()) {
            String price = template.getForObject(baseUrl + listing.ticker + "&f=p0", String.class);
            listing.prices.add(Double.parseDouble(price));
        }
    }

}
