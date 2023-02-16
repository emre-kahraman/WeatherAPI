package com.example.WeatherAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Weather implements Serializable {

    private LocalDate datetime;
    private Double tempmax;
    private Double tempmin;
    private Double temp;
    private Double windspeed;
    private String conditions;
    private String description;
}
