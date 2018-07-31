package com.graduate.training.service;
import com.graduate.training.entities.Strategy;

import java.util.List;

public interface StrategyService {
    void addStrategy(Strategy s);
    void runStrategies();
    Iterable<Strategy> getStrategies();
}
