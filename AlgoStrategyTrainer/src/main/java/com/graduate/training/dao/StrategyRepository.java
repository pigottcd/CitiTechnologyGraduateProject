package com.graduate.training.dao;

import com.graduate.training.entities.Strategy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface StrategyRepository extends CrudRepository<Strategy,Integer> {
    List<Strategy> findByActiveIsTrue();
    Strategy findById(Integer id);
}
