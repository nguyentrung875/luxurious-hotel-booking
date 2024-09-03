package com.java06.luxurious_hotel.service;


import com.java06.luxurious_hotel.dto.RoomTypeByDateDTO;
import com.java06.luxurious_hotel.dto.RoomVailableDTO;
import com.java06.luxurious_hotel.request.SearchRoomRequest;
import org.springframework.stereotype.Service;

import java.util.List;







@Service
public interface RoomService {
    List<RoomTypeByDateDTO> getRoomInfoByDate(String chooseDate);
    List<RoomVailableDTO> getAvailableRooms(SearchRoomRequest searchRoomRequest);
}
