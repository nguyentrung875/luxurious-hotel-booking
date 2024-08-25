package com.java06.luxurious_hotel.service;


import com.java06.luxurious_hotel.dto.RoomTypeDTO;
import com.java06.luxurious_hotel.request.AddRoomtypeRequest;
import com.java06.luxurious_hotel.request.UpdateRoomtypeRequest;

import java.util.List;

public interface RoomTypeService {

    void addRoomType(AddRoomtypeRequest addRoomtypeRequest);


    List<RoomTypeDTO> allRoomTypes();

    RoomTypeDTO findRoomTypeById(int id);

    boolean deleteRoomTypeById(int id);

    boolean updateRoomType(UpdateRoomtypeRequest updateRoomtypeRequest);
}
