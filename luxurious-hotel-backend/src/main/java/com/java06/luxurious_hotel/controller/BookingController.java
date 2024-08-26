package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.request.AddBookingRequest;
import com.java06.luxurious_hotel.response.APIResponse;
import com.java06.luxurious_hotel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> addBooking(@RequestBody AddBookingRequest request){
        bookingService.addNewBooking(request);

        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage("Thêm mới booking thành công");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
