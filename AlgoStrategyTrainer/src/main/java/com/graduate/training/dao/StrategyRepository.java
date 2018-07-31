package com.graduate.training.dao;

import com.graduate.training.entities.Strategy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface StrategyRepository extends CrudRepository<Strategy,Integer> {
    Iterable<Strategy> findByActiveIsTrue();
}
