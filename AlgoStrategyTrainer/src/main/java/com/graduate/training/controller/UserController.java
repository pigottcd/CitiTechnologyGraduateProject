package com.graduate.training.controller;


import com.graduate.training.entities.User;
import com.graduate.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@RestController
@RequestMapping("/users/")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(method = RequestMethod.GET)
    Iterable<User> findAll() {
        return service.getUsers();
    }

    @RequestMapping(method = RequestMethod.POST)
    void addUser(@RequestBody User user) {service.addToCatalog(user);}
}
