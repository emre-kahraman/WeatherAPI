package com.example.WeatherAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherDTO implements Serializable {

    private String resolvedAddress;
    private List<Weather> days;
}
