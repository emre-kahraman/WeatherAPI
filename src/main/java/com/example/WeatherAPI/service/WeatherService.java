package com.example.WeatherAPI.service;

import com.example.WeatherAPI.model.WeatherDTO;
import com.example.WeatherAPI.model.WeatherRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {

    private final RestTemplate restTemplate;
    @Value("${api.uri}")
    private String api;
    @Value("${api.key}")
    private String apiKey;

    public ResponseEntity<WeatherDTO> getDailyWeather(WeatherRequest weatherRequest) {
        String uri = api
                +weatherRequest.getCity()
                +","+weatherRequest.getCountry()
                +"/today?unitGroup=metric&key="
                +apiKey+"&contentType=json";
        log.info("daily Weather request with parameters city:{} country:{} to {}"
                , weatherRequest.getCity()
                , weatherRequest.getCountry()
                , uri);
        return restTemplate.getForEntity(uri, WeatherDTO.class);
    }

    public ResponseEntity<WeatherDTO> getWeeklyWeather(WeatherRequest weatherRequest) {
        String uri = api
                +weatherRequest.getCity()
                +","+weatherRequest.getCountry()
                +"/"+LocalDate.now()
                +"/"+LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                +"?unitGroup=metric&key="
                +apiKey+"&contentType=json";
        log.info("weekly Weather request with parameters city:{} country:{} to {}"
                , weatherRequest.getCity()
                , weatherRequest.getCountry()
                , uri);
        return restTemplate.getForEntity(uri, WeatherDTO.class);
    }

    public ResponseEntity<WeatherDTO> getMonthlyWeather(WeatherRequest weatherRequest) {
        String uri = api
                +weatherRequest.getCity()
                +","+weatherRequest.getCountry()
                +"/"+LocalDate.now()
                +"/" +LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())
                +"?unitGroup=metric&key="
                +apiKey+"&contentType=json";
        log.info("monthly Weather request with parameters city:{} country:{} to {}"
                , weatherRequest.getCity()
                , weatherRequest.getCountry()
                , uri);
        return restTemplate.getForEntity(uri, WeatherDTO.class);
    }
}
