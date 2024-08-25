package com.java06.luxurious_hotel.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AddRoomtypeRequest(

        String name,
        String overview,
        double price,
        double area,
        int capacity,
        int iDBedType,
        List<MultipartFile> images,
        List<Integer> idAmenity
        ) {
}
