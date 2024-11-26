package com.example.registry_login_logout.feign;

import com.example.registry_login_logout.dto.KeycloakUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "keycloakService", url = "http://localhost:8081")
public interface KeycloakServiceClient {

    @PostMapping("/users")
    void createUserInKeycloak(@RequestBody KeycloakUserRequest userRequest);
}
