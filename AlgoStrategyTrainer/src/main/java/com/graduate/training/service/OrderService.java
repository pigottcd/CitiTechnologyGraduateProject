package com.graduate.training.service;

import com.graduate.training.entities.Order;

import java.util.List;

public interface OrderService {
    Order getOrderByID(Integer id);
    List<Order> getOrderByStrategyID(Integer strategyID);
    void addOrder(Order order);

}
