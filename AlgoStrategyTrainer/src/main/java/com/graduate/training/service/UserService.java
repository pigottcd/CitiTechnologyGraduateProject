package com.graduate.training.service;

import com.graduate.training.entities.User;

public interface UserService {
    Iterable<User> getUsers();

    void addToCatalog(User user);
}
