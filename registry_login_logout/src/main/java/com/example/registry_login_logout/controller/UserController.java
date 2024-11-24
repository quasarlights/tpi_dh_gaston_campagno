package com.example.registry_login_logout.controller;

import com.example.registry_login_logout.model.Users;
import com.example.registry_login_logout.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody Users users) throws IOException {
        System.out.println(users);
        Users registeredUsers = userService.registerUser(users);
        return ResponseEntity.ok(registeredUsers);
    }

    @GetMapping("/hello")
    public String hello(){
        String message= "Hello World!";
        return message;
    }
}