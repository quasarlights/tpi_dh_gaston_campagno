package com.example.keycloack_service.service;

import com.example.keycloack_service.feign.KeycloakFeignClient;
import com.example.keycloack_service.model.AdminToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.TileObserver;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakUserService {

    @Autowired
    private KeycloakFeignClient keycloakFeignClient;

    @Autowired
    private AuthService authService; // Usa esto para obtener el token de admin

    @Value("${feign.client.config.keycloak.url}")
    private String keycloakBaseUrl;

    public void createUserInKeycloak(String username, String password, String email, String firstName, String lastName) {
        // Recuperamos el token de administrador
        String adminToken = authService.getAdminToken();
        String token = getAccessTokenFromJson(adminToken);
        System.out.println("ADMINTOKEN!! " + adminToken + "!!ADMINTOKEN");
        // Construimos el payload del usuario
        System.out.println("CLEANtOKEN!!" + token + "!!CLEANtOKEN");
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("enabled", true);

        Map<String, String> credentials = new HashMap<>();
        credentials.put("type", "password");
        credentials.put("value", password);
        credentials.put("temporary", "false");

        payload.put("credentials", Collections.singletonList(credentials));
        payload.put("email", email);
        payload.put("firstName", firstName);
        payload.put("lastName", lastName);

        // Hacemos la solicitud a Keycloak
        keycloakFeignClient.createUser("Bearer " + token, payload);
    }

    public String getAccessTokenFromJson(String jsonToken) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("jsonToken: " + jsonToken);
            JsonNode rootNode = objectMapper.readTree(jsonToken);
            return rootNode.path("access_token").asText();
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el token de administrador", e);
        }
    }

}
