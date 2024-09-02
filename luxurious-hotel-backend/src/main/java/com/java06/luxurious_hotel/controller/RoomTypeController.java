package com.java06.luxurious_hotel.controller;


import com.java06.luxurious_hotel.dto.RoomTypeDTO;
import com.java06.luxurious_hotel.request.AddRoomtypeRequest;
import com.java06.luxurious_hotel.request.UpdateRoomtypeRequest;
import com.java06.luxurious_hotel.response.BaseResponse;
import com.java06.luxurious_hotel.service.RoomTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roomType")
@CrossOrigin
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping
    public ResponseEntity<?> addRoomType(@Valid AddRoomtypeRequest addRoomtypeRequest){
        roomTypeService.addRoomType(addRoomtypeRequest);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData("New RoomType added successfully");




        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getListRoomType(){

        List<RoomTypeDTO> roomTypeDTOList= roomTypeService.allRoomTypes();

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(roomTypeDTOList);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomTypeDetail(@PathVariable int id){

        RoomTypeDTO roomTypeDTO = roomTypeService.findRoomTypeById(id);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(roomTypeDTO);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomType(@PathVariable int id){

        BaseResponse baseResponse = new BaseResponse();
        String data = "";
        boolean isSuccess = roomTypeService.deleteRoomTypeById(id);
        if (isSuccess) {

            data ="Delete Roomtype successfully";
        } else {

            data ="Delete Roomtype fail";
        }
        baseResponse.setData(data);


        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }


    @PutMapping
    public ResponseEntity<?> updateRoomType(@Valid UpdateRoomtypeRequest updateRoomtypeRequest){

        BaseResponse baseResponse = new BaseResponse();
        String data = "";
        boolean isSuccess = roomTypeService.updateRoomType(updateRoomtypeRequest);


        if (isSuccess) {
            data ="Update Roomtype successfully";
        }else {

            data ="Update room type fail, room type not found";

        }
        baseResponse.setData(data);
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }
}
