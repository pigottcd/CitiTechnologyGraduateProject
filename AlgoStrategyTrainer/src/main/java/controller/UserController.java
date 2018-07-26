package controller;


import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@RestController


public class UserController {

    @Autowireds
    private UserService service;
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    Iterable<User> findAll() {
        System.out.println("got request");
        return service.getUsers();
    }
}
