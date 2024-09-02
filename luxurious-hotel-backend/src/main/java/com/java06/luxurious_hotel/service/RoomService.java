package com.java06.luxurious_hotel.service;

import com.java06.luxurious_hotel.dto.RoomVailableDTO;
import com.java06.luxurious_hotel.request.SearchRoomRequest;

import java.util.List;

public interface RoomService {

    List<RoomVailableDTO> getAvailableRooms(SearchRoomRequest searchRoomRequest);
}
