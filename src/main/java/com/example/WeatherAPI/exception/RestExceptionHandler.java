package com.example.WeatherAPI.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(ApiError.builder()
                .httpStatusCode(exception.getStatusCode().value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(exception.getFieldError()
                        .getDefaultMessage())
                .dateTime(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleLocationNotFoundException(HttpClientErrorException exception){
        return new ResponseEntity<>(ApiError.builder()
                .httpStatusCode(exception.getStatusCode().value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(exception.getStatusText())
                .dateTime(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    protected ResponseEntity<Object> handleHttpServerException(HttpServerErrorException exception){
        return new ResponseEntity<>(ApiError.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getLocalizedMessage())
                .dateTime(LocalDateTime.now())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity<>(ApiError.builder()
                .httpStatusCode(exception.getStatusCode().value())
                .httpStatus(HttpStatus.METHOD_NOT_ALLOWED)
                .message(exception.getMessage())
                .dateTime(LocalDateTime.now())
                .build(), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
