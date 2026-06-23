package com.blogapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String,Object> handleResourceNotFound(ResourceNotFoundException ex){
        Map<String,Object> map = new HashMap<>();
        map.put("message", ex.getMessage());
        map.put("Timestamp", LocalDateTime.now());
        map.put("Trace", ex.getCause());
        map.put("Status code", HttpStatus.NOT_FOUND);
        return map;
    }

}
