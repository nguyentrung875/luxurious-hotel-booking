package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.dto.GuestDTO;
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
}
