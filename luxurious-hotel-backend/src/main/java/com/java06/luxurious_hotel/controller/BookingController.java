package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.request.AddBookingRequest;
import com.java06.luxurious_hotel.request.UpdateBookingRequest;
import com.java06.luxurious_hotel.response.BaseResponse;
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
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("New booking added successfully");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateBooking(@RequestBody UpdateBookingRequest request){
        bookingService.updateBooking(request);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Update booking successfully");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable int id){
        bookingService.deleteBooking(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Delete booking successfully");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
