package com.example.keycloack_service.controller;


import com.example.keycloack_service.dto.LoginRequest;
import com.example.keycloack_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String tokenResponse = authService.login(
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                loginRequest.getEmail()
        );
        return ResponseEntity.ok(tokenResponse);
    }

}
