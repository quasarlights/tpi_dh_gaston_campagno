package com.example.keycloack_service.service;

import com.example.keycloack_service.feign.KeycloakFeignClient;
import com.example.keycloack_service.feign.LoginFeignClient;
import com.example.keycloack_service.model.CachedToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.time.Instant;

@Service
public class AuthService {

    @Autowired
    private KeycloakFeignClient keycloakFeignClient;
    @Autowired
    private LoginFeignClient loginFeignClient;
    @Autowired
    private CacheManager cacheManager;
    @Value("${feign.client.config.keycloak.client-id}")
    private String clientId;

    @Value("${feign.client.config.keycloak.url-login}")
    private String keycloakTokenUrl;
    @Value("${feign.client.config.keycloak.client-secret:}")
    private String clientSecret; // Opcional si el cliente es público

    @Value("${feign.client.config.keycloak.realm}")
    private String realm;
    @Autowired
    private RestTemplate restTemplate;

    //LOGIN
    public String login(String username, String password, String email) {
        // Construir encabezados
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        // Construir el cuerpo de la solicitud
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("grant_type", "password");
        formData.add("username", username);
        formData.add("password", password);
        formData.add("email", email);
        formData.add("client_secret", clientSecret);

        // Crear la entidad HTTP
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);

        // Realizar la solicitud
        ResponseEntity<String> response = restTemplate.postForEntity(keycloakTokenUrl, requestEntity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            // Retornar la respuesta del token
            return response.getBody();
        } else {
            throw new RuntimeException("Error al autenticar con Keycloak: " + response.getStatusCode());
        }
    }

    //GET ADMIN TOKEN FOR CREATE USERS
    @Cacheable(value = "adminTokenCache", key = "'adminToken'")
    public String getAdminToken() {
        CachedToken cachedToken = getCachedToken();

        // Si el token está en caché y no ha expirado, lo devolvemos
        if (cachedToken != null && !cachedToken.isExpired()) {
            System.out.println("IS VALID? " + cachedToken.isExpired());
            return cachedToken.getToken();
        }

        // Si no está en caché o ha expirado, obtenemos un nuevo token
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "client_credentials");
        formData.add("client_id", "admin-service");
        formData.add("client_secret", "k0ffwii1RTQ37ISe58j3doQGMVICNPv2");

        String tokenResponse = keycloakFeignClient.getAdminToken(formData);

        // Suponiendo que el token de la respuesta incluye 'expires_in' en segundos
        int expiresIn = extractExpiresInFromTokenResponse(tokenResponse); // Implementa este método

        // Calculamos la hora de expiración
        Instant expirationTime = Instant.now().plusSeconds(expiresIn);
        System.out.println("EXPIRATION TIME: " + expirationTime);

        // Creamos un CachedToken y lo almacenamos en la caché
        CachedToken newToken = new CachedToken(tokenResponse, expirationTime);
        storeTokenInCache(newToken);

        return newToken.getToken();
    }

    private CachedToken getCachedToken() {
        // Intentamos recuperar el token de la caché
        Cache.ValueWrapper cachedValue = cacheManager.getCache("adminTokenCache").get("adminToken");
        if (cachedValue != null) {
            // Si existe, devolvemos el objeto CachedToken
            return (CachedToken) cachedValue.get();
        }
        // Si no existe, devolvemos null
        return null;
    }


    private void storeTokenInCache(CachedToken token) {
        // Guardamos el nuevo token en caché
        cacheManager.getCache("adminTokenCache").put("adminToken", token);
    }

    private int extractExpiresInFromTokenResponse(String tokenResponse) {
        try {
            // Crea un objeto ObjectMapper para procesar el JSON
            ObjectMapper mapper = new ObjectMapper();

            // Convierte la respuesta en un árbol JSON
            JsonNode jsonNode = mapper.readTree(tokenResponse);
            System.out.println("EXPIRE IN LOGGEO : " + jsonNode.get("expires_in").asInt());
            // Extrae el valor de "expires_in"
            return jsonNode.get("expires_in").asInt();
        } catch (Exception e) {
            // Maneja posibles excepciones, como errores de parseo
            throw new RuntimeException("Error al procesar la respuesta del token", e);
        }
    }
}
