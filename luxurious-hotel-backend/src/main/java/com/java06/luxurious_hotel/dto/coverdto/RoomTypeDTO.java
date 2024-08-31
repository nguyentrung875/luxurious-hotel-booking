package com.java06.luxurious_hotel.dto.coverdto;

import lombok.Data;

import java.util.List;

@Data
public class RoomTypeDTO {
    private String nameRoomType;
    private List<String> roomNumber;
}
