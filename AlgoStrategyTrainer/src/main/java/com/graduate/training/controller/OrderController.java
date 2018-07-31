package com.graduate.training.controller;

import com.graduate.training.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService service;

    // Need to implement requests
}
