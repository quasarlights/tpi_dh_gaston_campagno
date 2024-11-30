package com.example.cards.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8083")
public interface AccountClient {
    @GetMapping("/accounts/{id}")
    Boolean getAccountById(@PathVariable("id") Long accountId);
}
