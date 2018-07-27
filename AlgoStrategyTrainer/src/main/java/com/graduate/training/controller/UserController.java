package com.graduate.training.controller;


import com.graduate.training.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.graduate.training.service.UserService;

@RestController

@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService service;
    @RequestMapping(method = RequestMethod.GET)
    Iterable<User> findAll() {
        System.out.println("got request");
        return service.getUsers();
    }
}
