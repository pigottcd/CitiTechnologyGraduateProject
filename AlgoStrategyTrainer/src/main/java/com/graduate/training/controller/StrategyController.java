package com.graduate.training.controller;

import com.graduate.training.entities.Strategy;
import com.graduate.training.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

@RestController
@RequestMapping("/strategies/")
@CrossOrigin
public class StrategyController {
    private static final Logger LOGGER = LogManager.getLogger(StrategyController.class);
    @Autowired
    private StrategyService service;
    @GetMapping
    Iterable<Strategy> findAll() {
        LOGGER.info("Got GET request for all strategies");
        return service.getStrategies();
    }

    @GetMapping(value = "active/")
    List<Strategy> findAllActive() {
        LOGGER.info("Got GET request for all active strategies");
        return service.getActiveStrategies();
    }

    @GetMapping(value = "strategy_id/{id}")
    public Strategy getStrategyById(@PathVariable Integer id){
        LOGGER.info("Got GET request for strategy id: " + id);
        return service.getStrategyById(id);
    }

    @PostMapping
    void addStrategy(@RequestBody Strategy strategy) {
        LOGGER.info("Got POST request for new strategy");
        service.addStrategy(strategy);
    }

    @DeleteMapping(value = "strategy_id/{id}")
    public void deactivateStrategy(@PathVariable Integer id){
        LOGGER.info("Got DELETE request for strategy id: " + id);
        service.deactivateStrategy(id);
    }



}   
