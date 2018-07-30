package com.graduate.training.service;

import java.util.List;

public interface PriceFeedService {
    void register(String ticker);
    void deregister(String ticker);
    double getCurrentPrice(String ticker);
    List<Double> getPriceRange(String ticker);
    List<Double> getPriceRange(String ticker, int range);
}
