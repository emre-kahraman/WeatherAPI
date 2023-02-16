package com.example.WeatherAPI.controller;

import com.example.WeatherAPI.model.WeatherDTO;
import com.example.WeatherAPI.model.WeatherRequest;
import com.example.WeatherAPI.service.WeatherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weathers")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @PostMapping("/daily")
    public ResponseEntity<WeatherDTO> getDailyWeather(@Valid @RequestBody WeatherRequest weatherRequest){
        return weatherService.getDailyWeather(weatherRequest);
    }

    @PostMapping("/weekly")
    public ResponseEntity<WeatherDTO> getWeeklyWeather(@Valid @RequestBody WeatherRequest weatherRequest){
        return weatherService.getWeeklyWeather(weatherRequest);
    }

    @PostMapping("/monthly")
    public ResponseEntity<WeatherDTO> getMonthlyWeather(@Valid @RequestBody WeatherRequest weatherRequest){
        return weatherService.getMonthlyWeather(weatherRequest);
    }
}
