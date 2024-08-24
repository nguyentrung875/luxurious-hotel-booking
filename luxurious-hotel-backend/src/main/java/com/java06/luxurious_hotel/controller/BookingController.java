package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.payload.request.AddBookingRequest;
import com.java06.luxurious_hotel.payload.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @PostMapping
    public ResponseEntity<?> addBooking(@RequestBody AddBookingRequest request){
        System.out.println(request.checkOutDate());
//        APIResponse apiResponse = new APIResponse();
//        apiResponse.setData(request);

        APIResponse apiResponse = APIResponse.builder()
                .data(request)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
