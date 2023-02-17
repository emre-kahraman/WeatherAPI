package com.example.WeatherAPI;

import com.example.WeatherAPI.model.WeatherDTO;
import com.example.WeatherAPI.model.WeatherRequest;
import com.example.WeatherAPI.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class WeatherServiceTests {

    @Autowired
    WeatherService weatherService;

    @Test
    public void itShouldReturnDailyWeather(){
        WeatherRequest weatherRequest = WeatherRequest.builder().city("london").country("england").build();

        ResponseEntity<WeatherDTO> responseEntity = weatherService.getDailyWeather(weatherRequest);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getResolvedAddress().toLowerCase().contains(weatherRequest.getCity()), true);
        assertEquals(responseEntity.getBody().getDays().size(), 1);
    }

    @Test
    public void itShouldReturnWeeklyWeather(){
        WeatherRequest weatherRequest = WeatherRequest.builder().city("london").country("england").build();

        ResponseEntity<WeatherDTO> responseEntity = weatherService.getWeeklyWeather(weatherRequest);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getResolvedAddress().toLowerCase().contains(weatherRequest.getCity()), true);
        assertEquals(responseEntity.getBody().getDays().size(), 8-LocalDate.now().getDayOfWeek().getValue());
    }

    @Test
    public void itShouldReturnMonthlyWeather(){
        WeatherRequest weatherRequest = WeatherRequest.builder().city("london").country("england").build();

        ResponseEntity<WeatherDTO> responseEntity = weatherService.getMonthlyWeather(weatherRequest);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getResolvedAddress().toLowerCase().contains(weatherRequest.getCity()), true);
        assertEquals(responseEntity.getBody().getDays().size(), LocalDate.now().lengthOfMonth()-LocalDate.now().getDayOfMonth()+1);
    }
}
