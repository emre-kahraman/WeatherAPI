package com.example.WeatherAPI.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        String result = new BufferedReader(new InputStreamReader(response.getBody()))
                .lines().collect(Collectors.joining("\n"));
        switch(response.getStatusCode().value()) {
            case(400) -> {
                log.error("HttpClientError httpStatus:{} message:{}", response.getStatusCode(), result);
                throw new HttpClientErrorException(response.getStatusCode(), result);
            }
            case(500) -> {
                log.error("HttpServerError httpStatus:{} message:{}", response.getStatusCode(), result);
                throw new HttpServerErrorException(response.getStatusCode(), result);
            }
        }
    }
}
