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
    @RequestMapping(method = RequestMethod.GET)
    Iterable<Strategy> findAll() {return service.getStrategies();}

    @GetMapping(value = "active/")
    List<Strategy> findAllActive() {return service.getActiveStrategies();}

    @PostMapping
    void addStrategy(@RequestBody Strategy strategy) {service.addStrategy(strategy);}


}   
