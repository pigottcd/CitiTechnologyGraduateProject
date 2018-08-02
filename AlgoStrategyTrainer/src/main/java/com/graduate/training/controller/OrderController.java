package com.graduate.training.controller;

import com.graduate.training.entities.Order;
import com.graduate.training.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

@RestController
@CrossOrigin
public class OrderController {
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);
    @Autowired
    private OrderService service;

    @GetMapping(value = "/orders/strategy_id/{id}")
    public List<Order> findOrdersByStrategyId(@PathVariable Integer id){
        LOGGER.info("Got GET request for order id: " + id);
        return service.getOrderByStrategyID(id);
    }
}
