package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.dto.BookingGuestDTO;
import com.java06.luxurious_hotel.dto.GuestDTO;
import com.java06.luxurious_hotel.entity.BookingEntity;
import com.java06.luxurious_hotel.request.AddGuestRequest;
import com.java06.luxurious_hotel.request.UpdateGuestRequest;
import com.java06.luxurious_hotel.response.BaseResponse;
import com.java06.luxurious_hotel.service.BookingService;
import com.java06.luxurious_hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @GetMapping()
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>("Hello user luxurious", HttpStatus.OK);
    }

    @PostMapping("/guests")
    public ResponseEntity<?> getAllGuest(){
        List<GuestDTO> listGuest = userService.getListGuest("ROLE_GUEST");
        System.out.println("test = " + listGuest);
        return new ResponseEntity<>(listGuest , HttpStatus.OK);
    }

    @PostMapping("/allbookingguest")
    public ResponseEntity<?> getBookingGuest(@RequestParam int idGuest){
        List<BookingGuestDTO> guestDTOS = bookingService.getListBooking(idGuest);
        BaseResponse response = new BaseResponse();
        response.setData(guestDTOS);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addguest")
    public ResponseEntity<?> addGuest(AddGuestRequest addGuestRequest){
        Boolean check = userService.addGuest(addGuestRequest);
        BaseResponse response = new BaseResponse();
        response.setData(check);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/deleteguest")
    public ResponseEntity<?> deleteGuest(@RequestParam int idGuest){
        BaseResponse response = new BaseResponse();
        response.setData(userService.deleteUser(idGuest));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/updateguest")
    public ResponseEntity<?> updateGuest(UpdateGuestRequest updateGuestRequest){

        BaseResponse response = new BaseResponse();
        response.setData(userService.updateUser(updateGuestRequest));

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
