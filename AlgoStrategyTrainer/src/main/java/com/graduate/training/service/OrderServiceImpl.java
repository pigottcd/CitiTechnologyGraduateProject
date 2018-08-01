package com.graduate.training.service;

import com.graduate.training.dao.OrderRepository;
import com.graduate.training.entities.Order;
import com.graduate.training.messaging.ActiveMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private ActiveMQSender sender;
    private OrderRepository dao;

    @Autowired
    public OrderServiceImpl(ActiveMQSender sender,  OrderRepository dao){
        this.sender = sender;
        this.dao = dao;
    }

    public Order getOrderByID(Integer id){
        return dao.findById(id);
    }

    public Iterable<Order> getOrderByStrategyID(Integer strategyID){
        return dao.findByStrategyId(strategyID);
    }

    public void addOrder(Order order){
        dao.save(order);
        sender.send(order);
    }
}
