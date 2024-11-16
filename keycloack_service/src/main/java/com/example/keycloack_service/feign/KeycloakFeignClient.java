package com.example.keycloack_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "keycloak-client", url = "${feign.client.config.keycloak.url}")
public interface KeycloakFeignClient {

    @PostMapping("${feign.client.config.keycloak.token-path}")
    String getAdminToken(@RequestBody MultiValueMap<String, String> formData);
}