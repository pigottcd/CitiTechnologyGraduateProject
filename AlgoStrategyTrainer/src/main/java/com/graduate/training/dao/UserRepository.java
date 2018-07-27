package com.graduate.training.dao;

import com.graduate.training.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//using crud repository for database accesses,
@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
}

