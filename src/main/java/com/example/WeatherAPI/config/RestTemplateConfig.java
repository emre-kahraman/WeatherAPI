package com.example.WeatherAPI.config;

import com.example.WeatherAPI.exception.RestTemplateErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());
        return restTemplate;
    }
}
