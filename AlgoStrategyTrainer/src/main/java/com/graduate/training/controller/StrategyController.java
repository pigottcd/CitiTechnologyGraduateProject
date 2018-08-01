package com.graduate.training.controller;

import com.graduate.training.entities.Strategy;
import com.graduate.training.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/strategies/")
@CrossOrigin
public class StrategyController {

    @Autowired
    private StrategyService service;
    @GetMapping
    Iterable<Strategy> findAll() {return service.getStrategies();}

    @GetMapping(value = "active/")
    List<Strategy> findAllActive() {return service.getActiveStrategies();}

    @GetMapping(value = "strategy_id/{id}")
    public Strategy getStrategyById(@PathVariable Integer id){
        return service.getStrategyById(id);
    }

    @PostMapping
    void addStrategy(@RequestBody Strategy strategy) {
        service.addStrategy(strategy);
    }

    @DeleteMapping(value = "strategy_id/{id}")
    public void deactivateStrategy(@RequestBody Integer id){
        service.deactivateStrategy(id);
    }



}   
