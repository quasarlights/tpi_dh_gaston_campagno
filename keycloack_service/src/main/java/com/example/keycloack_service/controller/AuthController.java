package com.example.keycloack_service.controller;


import com.example.keycloack_service.dto.LoginRequest;
import com.example.keycloack_service.dto.TokenResponse;
import com.example.keycloack_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/admin-token")
    public ResponseEntity<String> getAdminToken() {
        try {
            String token = authService.getAdminToken();
            return ResponseEntity.ok(token);  // Devuelve el token en la respuesta
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error obteniendo el token: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                loginRequest.getEmail()
        );
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody Map<String, String> requestBody) {
        String refreshToken = requestBody.get("refresh_token");
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("Refresh token is required");
        }
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

}
