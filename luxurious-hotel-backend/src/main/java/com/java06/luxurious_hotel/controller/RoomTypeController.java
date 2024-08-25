package com.java06.luxurious_hotel.controller;


import com.java06.luxurious_hotel.dto.RoomTypeDTO;
import com.java06.luxurious_hotel.request.AddRoomtypeRequest;
import com.java06.luxurious_hotel.request.UpdateRoomtypeRequest;
import com.java06.luxurious_hotel.service.RoomTypeService;
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
    public ResponseEntity<?> addRoomType(AddRoomtypeRequest addRoomtypeRequest){
        roomTypeService.addRoomType(addRoomtypeRequest);


        return new ResponseEntity<>("addroom type ne ne", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getListRoomType(){

        List<RoomTypeDTO> roomTypeDTOList= roomTypeService.allRoomTypes();
        return new ResponseEntity<>(roomTypeDTOList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomType(@PathVariable int id){

        RoomTypeDTO roomTypeDTO = roomTypeService.findRoomTypeById(id);

        return new ResponseEntity<>(roomTypeDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoomType(@PathVariable int id){

        boolean isSuccess = roomTypeService.deleteRoomTypeById(id);
        if (isSuccess) {
            // Xóa thành công, trả về 204 No Content
            return new ResponseEntity<>("Delete room type success", HttpStatus.OK);
        } else {
            // Xóa thất bại, trả về 404 Not Found
            return new ResponseEntity<>("Delete room type fail, room type not found", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping
    public ResponseEntity<?> updateRoomType(UpdateRoomtypeRequest updateRoomtypeRequest){

        boolean isSuccess = roomTypeService.updateRoomType(updateRoomtypeRequest);


        if (isSuccess) {
            return new ResponseEntity<>("Update room type success", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Update room type fail, room type not found", HttpStatus.NOT_FOUND);
        }
    }
}
