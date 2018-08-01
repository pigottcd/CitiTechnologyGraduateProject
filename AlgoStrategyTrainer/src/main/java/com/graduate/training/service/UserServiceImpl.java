package com.graduate.training.service;

import com.graduate.training.dao.UserRepository;
import com.graduate.training.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository dao;

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<User> getUsers() {
        return dao.findAll();
    }

    @Transactional (propagation = Propagation.REQUIRED)
    public void addToCatalog(User user) {dao.save(user);}


}
