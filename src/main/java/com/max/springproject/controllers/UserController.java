package com.max.springproject.controllers;

import com.max.springproject.models.User;
import com.max.springproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public String getUsers() {
        return service.getAll().toString();
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable("id") long id){
        return service.getById(id).toString();
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") User user) {
        service.save(user);
        return service.getAll().toString();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        service.deleteById(id);
        return service.getAll().toString();
    }
}
