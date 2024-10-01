package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.dto.BookingGuestDTO;
import com.java06.luxurious_hotel.dto.GuestDTO;
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

        List<GuestDTO> listGuest = userService.getListGuest();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(listGuest);

        return new ResponseEntity<>(baseResponse , HttpStatus.OK);
    }

    @PostMapping("/allbookingguest")
    public ResponseEntity<?> getBookingGuest(@RequestParam int idGuest){
        List<BookingGuestDTO> guestDTOS = bookingService.getListBooking(idGuest);
        BaseResponse response = new BaseResponse();
        response.setData(guestDTOS);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addguest")
    public ResponseEntity<?> addGuest(@RequestBody AddGuestRequest addGuestRequest){
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
    public ResponseEntity<?> updateGuest(@RequestBody UpdateGuestRequest updateGuestRequest){

        BaseResponse response = new BaseResponse();
        response.setData(userService.updateUser(updateGuestRequest));

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/getvalueuserbyid/{idUser}")
    public ResponseEntity<?> getValueUserById(@PathVariable int idUser){

        BaseResponse response = new BaseResponse();
        response.setData(userService.getUser(idUser));

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/checkemail")
    public ResponseEntity<?> checkEmail(@RequestParam String email){

        BaseResponse response = new BaseResponse();
        response.setData(userService.checkEmail(email));

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/checkphone")
    public ResponseEntity<?> checkPhone(@RequestParam String phone){

        BaseResponse response = new BaseResponse();
        response.setData(userService.checkPhone(phone));

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
