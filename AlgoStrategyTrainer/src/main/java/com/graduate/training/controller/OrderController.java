package com.graduate.training.controller;

import com.graduate.training.entities.Order;
import com.graduate.training.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/orders/")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping(value = "/orders/strategy_id/{id}")
    public List<Order> findOrdersByStrategyId(@PathVariable Integer id){
        return service.getOrderByStrategyID(id);
    }
    // Need to implement requests
}
