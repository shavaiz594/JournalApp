package org.example.controller;

import org.example.Service.Userservice;
import org.example.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class Publiccontroller {
    @Autowired
    Userservice userservice;
    @GetMapping
    public List<UserEntry> getUsers() {
        return userservice.Getall();
    }
    @PostMapping
    public void createUser(@RequestBody UserEntry userentry) {
        userservice.Saveentry(userentry);
    }
}
