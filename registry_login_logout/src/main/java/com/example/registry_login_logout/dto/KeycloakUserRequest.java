package com.example.registry_login_logout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeycloakUserRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
}
