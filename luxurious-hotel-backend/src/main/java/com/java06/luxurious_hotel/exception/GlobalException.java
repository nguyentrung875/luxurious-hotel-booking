package com.java06.luxurious_hotel.exception;

import com.java06.luxurious_hotel.exception.roomType.AmentityNotFoundException;
import com.java06.luxurious_hotel.exception.roomType.RoomTypeNotFoundException;
import com.java06.luxurious_hotel.payload.response.APIResponse;
import com.java06.luxurious_hotel.response.BaseResponse;
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

    @ExceptionHandler(RoomTypeNotFoundException.class)
    public ResponseEntity<?> handleRoomTypeNotFoundException(RoomTypeNotFoundException e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


    @ExceptionHandler(AmentityNotFoundException.class)
    public ResponseEntity<?> handleAmentityNotFoundException(AmentityNotFoundException e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
