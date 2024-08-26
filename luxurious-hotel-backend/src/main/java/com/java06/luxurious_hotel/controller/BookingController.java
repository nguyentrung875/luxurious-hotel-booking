package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.request.AddBookingRequest;
import com.java06.luxurious_hotel.response.BaseResponse;
import com.java06.luxurious_hotel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> addBooking(@RequestBody AddBookingRequest request){
        bookingService.addNewBooking(request);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("New booking added successfully");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
