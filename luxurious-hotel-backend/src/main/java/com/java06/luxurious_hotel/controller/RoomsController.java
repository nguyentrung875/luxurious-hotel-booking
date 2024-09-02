package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.dto.RoomTypeDTO;
import com.java06.luxurious_hotel.dto.RoomVailableDTO;
import com.java06.luxurious_hotel.request.SearchRoomRequest;
import com.java06.luxurious_hotel.response.BaseResponse;
import com.java06.luxurious_hotel.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/room")
@CrossOrigin
public class RoomsController {

    @Autowired
    private RoomService roomService;


    @GetMapping("/checkRoom")
    public ResponseEntity<?> getAvailableRoom(@Valid SearchRoomRequest searchRoomRequest) {


        List<RoomVailableDTO> roomVailableDTOList = roomService.getAvailableRooms(searchRoomRequest);
        System.out.println("so luọng là : " + roomVailableDTOList.size());
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(roomVailableDTOList);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


}
