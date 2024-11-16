package com.example.keycloack_service.model;

import java.time.Instant;

public class CachedToken {

    private String token;
    private Instant expirationTime;

    public CachedToken(String token, Instant expirationTime) {
        this.token = token;
        this.expirationTime = expirationTime;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expirationTime);
    }
}
