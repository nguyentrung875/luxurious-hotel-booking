package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.response.BaseResponse;
import com.java06.luxurious_hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/{selectDate}")
    public ResponseEntity<?> getAllRoomInfo(@PathVariable String selectDate){
        BaseResponse baseResponse = new BaseResponse();
        var s = roomService.getRoomInfoByDate(selectDate);
        baseResponse.setData(roomService.getRoomInfoByDate(selectDate));
        return new ResponseEntity(baseResponse, HttpStatus.OK);
    }
}
