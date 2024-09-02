package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.request.AuthenRequest;
import com.java06.luxurious_hotel.response.BaseResponse;
import com.java06.luxurious_hotel.service.AuthenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authen")
public class AuthenController {

    @Autowired
    private AuthenService authenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenRequest request){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Successful!");
        baseResponse.setData(authenService.login(request));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
