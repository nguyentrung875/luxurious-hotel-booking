package com.java06.luxurious_hotel.exception;

import com.java06.luxurious_hotel.payload.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(Exception e) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatusCode(500);
        apiResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
