package com.example.WeatherAPI.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class WeatherRequest {

    @NotBlank(message = "city can not be empty")
    private String city;
    @NotBlank(message = "country can not be empty")
    private String country;
}
