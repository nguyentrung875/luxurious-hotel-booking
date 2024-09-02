package com.java06.luxurious_hotel.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoomTypeDTO {

    private int id;
    private String name;
    private String overview;
    private double price;
    private double area;
    private int capacity;
    private String bedName;
    private List<String> roomName;
    private List<String> image;
    private String amenity;

}
