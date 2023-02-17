package com.example.WeatherAPI;

import com.example.WeatherAPI.exception.ApiError;
import com.example.WeatherAPI.model.WeatherDTO;
import com.example.WeatherAPI.model.WeatherRequest;
import com.example.WeatherAPI.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherApiIT {

    @Autowired
    TestRestTemplate restTemplate;
    @Autowired
    WeatherService weatherService;
    @LocalServerPort
    int port;

    @Test
    public void itShouldReturnDailyWeatherWithResponseOK(){
        WeatherRequest weatherRequest = WeatherRequest.builder().city("london").country("england").build();
        String uri = "http://localhost:"+port+"/api/weathers/daily";

        ResponseEntity<WeatherDTO> responseEntity = restTemplate.postForEntity(uri, weatherRequest, WeatherDTO.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getResolvedAddress().toLowerCase().contains(weatherRequest.getCity()), true);
        assertEquals(responseEntity.getBody().getResolvedAddress().toLowerCase().contains(weatherRequest.getCountry()), true);
        assertEquals(responseEntity.getBody().getDays().size(), 1);
    }

    @Test
    public void itShouldReturnWeeklyWeatherWithResponseOK(){
        WeatherRequest weatherRequest = WeatherRequest.builder().city("london").country("england").build();
        String uri = "http://localhost:"+port+"/api/weathers/weekly";

        ResponseEntity<WeatherDTO> responseEntity = restTemplate.postForEntity(uri, weatherRequest, WeatherDTO.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getResolvedAddress().toLowerCase().contains(weatherRequest.getCity()), true);
        assertEquals(responseEntity.getBody().getResolvedAddress().toLowerCase().contains(weatherRequest.getCountry()), true);
        assertEquals(responseEntity.getBody().getDays().size(), 8- LocalDate.now().getDayOfWeek().getValue());
    }

    @Test
    public void itShouldReturnMonthlyWeatherWithResponseOK(){
        WeatherRequest weatherRequest = WeatherRequest.builder().city("london").country("england").build();
        String uri = "http://localhost:"+port+"/api/weathers/monthly";

        ResponseEntity<WeatherDTO> responseEntity = restTemplate.postForEntity(uri, weatherRequest, WeatherDTO.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(responseEntity.getBody().getResolvedAddress().toLowerCase().contains(weatherRequest.getCity()), true);
        assertEquals(responseEntity.getBody().getResolvedAddress().toLowerCase().contains(weatherRequest.getCountry()), true);
        assertEquals(responseEntity.getBody().getDays().size(), LocalDate.now().lengthOfMonth()-LocalDate.now().getDayOfMonth()+1);
    }

    @Test
    public void itShouldReturnMethodArgumentNotValidWithResponseBAD_REQUEST(){
        WeatherRequest weatherRequest = WeatherRequest.builder().city("").country("england").build();
        String uri = "http://localhost:"+port+"/api/weathers/daily";

        ResponseEntity<ApiError> responseEntity = restTemplate.postForEntity(uri, weatherRequest, ApiError.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getHttpStatusCode(), HttpStatus.BAD_REQUEST.value());
        assertEquals(responseEntity.getBody().getHttpStatus(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getMessage(), "city can not be empty");
    }

    @Test
    public void itShouldReturnHttpClientErrorWithResponseBAD_REQUEST(){
        WeatherRequest weatherRequest = WeatherRequest.builder().city("asdqwdqfas").country("asdqwdwdsa").build();
        String uri = "http://localhost:"+port+"/api/weathers/daily";

        ResponseEntity<ApiError> responseEntity = restTemplate.postForEntity(uri, weatherRequest, ApiError.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(responseEntity.getBody().getHttpStatusCode(), HttpStatus.BAD_REQUEST.value());
        assertEquals(responseEntity.getBody().getHttpStatus(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void itShouldReturnApiErrorWithResponseMETHOD_NOT_ALLOWED(){
        WeatherRequest weatherRequest = WeatherRequest.builder().city("").country("england").build();
        String uri = "http://localhost:"+port+"/api/weathers/daily";

        ResponseEntity<ApiError> responseEntity = restTemplate.getForEntity(uri, ApiError.class);

        assertEquals(responseEntity.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);
        assertEquals(responseEntity.getBody().getHttpStatusCode(), HttpStatus.METHOD_NOT_ALLOWED.value());
        assertEquals(responseEntity.getBody().getHttpStatus(), HttpStatus.METHOD_NOT_ALLOWED);
        assertEquals(responseEntity.getBody().getMessage(), "Request method 'GET' is not supported");
    }
}
