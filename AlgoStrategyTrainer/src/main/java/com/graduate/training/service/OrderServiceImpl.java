package com.graduate.training.service;

import com.graduate.training.dao.OrderRepository;
import com.graduate.training.entities.Order;
import com.graduate.training.messaging.ActiveMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);
    private ActiveMQSender sender;
    private OrderRepository dao;

    @Autowired
    public OrderServiceImpl(ActiveMQSender sender,  OrderRepository dao){
        this.sender = sender;
        this.dao = dao;
    }

    public Order getOrderByID(Integer id){
        LOGGER.info("Getting order: " + id);
        return dao.findById(id);
    }

    public List<Order> getOrderByStrategyID(Integer strategyID){
        LOGGER.info("Getting Orders By strat Id: " + strategyID);
        return dao.findByStrategyId(strategyID);
    }

    public void addOrder(Order order){
        LOGGER.info("Adding new order: " + order.getId());
        dao.save(order);
        sender.send(order);
    }
}
