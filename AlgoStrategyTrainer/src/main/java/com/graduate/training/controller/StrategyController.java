package com.graduate.training.controller;

import com.graduate.training.entities.Strategy;
import com.graduate.training.service.StrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin
public class StrategyController {

    @Autowired
    private StrategyService service;
    @RequestMapping(method = RequestMethod.GET)
    Iterable<Strategy> findAll() {return service.getStrategies();}

    @PostMapping(value = "/strategies")
    void addStrategy(@RequestBody Strategy strategy) {service.addStrategy(strategy);}

}   
