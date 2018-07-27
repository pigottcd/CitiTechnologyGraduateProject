package com.graduate.training.service;

import com.graduate.training.daoe.UserRepository;
import com.graduate.training.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService {

    @Autowired
    private UserRepository dao;

    public Collection<User> getUsers() {
        return makeCollection(dao.findAll());
    }

    //dao's findAll returns an Iterable, but getUsers returns a Collection, switching to array list collection
    private static Collection<User> makeCollection(Iterable<User> itr) {
        Collection<User> users = new ArrayList<>();
        for (User u : itr) {
            users.add(u);
        }
        return users;
    }
}
