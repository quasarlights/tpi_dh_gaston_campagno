package com.example.keycloack_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import feign.gson.GsonEncoder;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.nio.charset.StandardCharsets;


@Configuration
public class FeignConfig {

    @Bean
    public Encoder feignEncoder() {
        return new GsonEncoder(); // O cualquier otro encoder que prefieras.
    }

    @Bean
    public HttpMessageConverter<?> formHttpMessageConverter() {
        return new FormHttpMessageConverter();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // Log adicional para ver si el interceptor es invocado
                System.out.println("Interceptor Invocado: " + template.request());
                System.out.println("Feing Request Body: " + new String(template.body(), StandardCharsets.UTF_8));
            }
        };
    }
}