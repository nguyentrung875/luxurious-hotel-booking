package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.dto.searchAvaiRoom.RoomTypeAvailableDTO;
import com.java06.luxurious_hotel.request.SearchRoomRequest;
import com.java06.luxurious_hotel.response.BaseResponse;
import com.java06.luxurious_hotel.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {


    @Autowired
    private RoomService roomService;


    @GetMapping
    public ResponseEntity<?> getAvailableRoom(@Valid SearchRoomRequest searchRoomRequest) {

        List<RoomTypeAvailableDTO> roomVailableDTOList = roomService.getAvailableRooms(searchRoomRequest);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(roomVailableDTOList);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> getAvailableRoomPost(@Valid @RequestBody SearchRoomRequest searchRoomRequest) {

        List<RoomTypeAvailableDTO> roomVailableDTOList = roomService.getAvailableRooms(searchRoomRequest);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(roomVailableDTOList);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }



    @GetMapping("/{selectDate}")
    public ResponseEntity<?> getAllRoomInfo(@PathVariable String selectDate){
        BaseResponse baseResponse = new BaseResponse();
        var s = roomService.getRoomInfoByDate(selectDate);
        baseResponse.setData(roomService.getRoomInfoByDate(selectDate));
        return new ResponseEntity(baseResponse, HttpStatus.OK);
    }


}
