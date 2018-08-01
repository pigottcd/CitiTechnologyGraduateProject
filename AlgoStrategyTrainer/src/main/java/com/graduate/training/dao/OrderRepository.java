package com.graduate.training.dao;

import com.graduate.training.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByStrategyId(Integer strategyId);
    Order findById(Integer id);
}
