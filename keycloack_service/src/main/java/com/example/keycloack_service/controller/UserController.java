package com.example.keycloack_service.controller;

import com.example.keycloack_service.dto.UserRequest;
import com.example.keycloack_service.feign.KeycloakFeignClient;
import com.example.keycloack_service.service.KeycloakUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private KeycloakUserService keycloakUserService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) {
        try {
            keycloakUserService.createUserInKeycloak(userRequest.getUsername(), userRequest.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating user: " + e.getMessage());
        }
    }
}
