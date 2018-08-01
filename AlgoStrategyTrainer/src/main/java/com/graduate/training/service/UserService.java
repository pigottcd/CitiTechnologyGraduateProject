package com.graduate.training.service;

import com.graduate.training.entities.User;

import java.util.List;

public interface UserService {
    Iterable<User> getUsers();

    void addToCatalog(User user);
}
