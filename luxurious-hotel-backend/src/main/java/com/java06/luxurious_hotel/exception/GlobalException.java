package com.java06.luxurious_hotel.exception;

import com.java06.luxurious_hotel.exception.booking.BookingNotFoundException;
import com.java06.luxurious_hotel.exception.room.RoomNotAvailableException;
import com.java06.luxurious_hotel.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(Exception e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(500);
        baseResponse.setMessage(e.getMessage() + " class: " + e.getClass()
        );
        return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    //Bắt các lỗi về chứng thực
//    @ExceptionHandler(value = AccessDeniedException.class)
//    public ResponseEntity<?> handlingAccessDeniedException(AccessDeniedException e) {
//        BaseResponse baseResponse = new BaseResponse();
//        baseResponse.setStatusCode(500);
//        baseResponse.setMessage(e.getMessage());
//
//        return new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
//    }
//

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handeValidation(MethodArgumentNotValidException e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(500);
        baseResponse.setMessage(e.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<?> handleRoomNotAvailableException(RoomNotAvailableException e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<?> handleBookingNotFoundException(BookingNotFoundException e) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage(e.getMessage());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
