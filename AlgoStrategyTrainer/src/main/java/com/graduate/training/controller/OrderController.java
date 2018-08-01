package com.graduate.training.controller;

import com.graduate.training.entities.Order;
import com.graduate.training.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/orders/")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping(value = "/orders/strategy_id/{id}")
    public Iterable<Order> findOrdersByStrategyId(@PathVariable Integer id){
        Iterable<Order> orderIterables = service.getOrderByStrategyID(id);
        return orderIterables;
    }
    // Need to implement requests
}
