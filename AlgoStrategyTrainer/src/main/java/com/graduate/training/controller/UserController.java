package com.graduate.training.controller;


import com.graduate.training.entities.User;
import com.graduate.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.graduate.training.service.UserServiceImpl;

@RestController

@RequestMapping("/")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;
    @RequestMapping(method = RequestMethod.GET)
    Iterable<User> findAll() {
        return service.getUsers();
    }
}
