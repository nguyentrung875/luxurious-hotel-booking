package com.java06.luxurious_hotel.request;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record UpdateRoomtypeRequest(
        @NotNull(message = "id not null")
        @NotBlank(message = "id not blank")
        int id,

        @NotNull(message = "name not null")
        @NotBlank(message = "name not blank")
        String name,


        @NotNull(message = "overview not null")
        @NotBlank(message = "overview not blank")
        String overview,


        @NotNull(message = "price not null")
        @NotBlank(message = "price not blank")
        double price,


        @NotNull(message = "area not null")
        @NotBlank(message = "area not blank")
        double area,


        @NotNull(message = "capacity not null")
        @NotBlank(message = "capacity not blank")
        int capacity,


        @NotNull(message = "iDBedType not null")
        @NotBlank(message = "iDBedType not blank")
        int iDBedType,

        @NotNull(message = "image not null")
        @NotBlank(message = "image not blank")
        List<MultipartFile> images,

        @NotNull(message = "idAmenity not null")
        @NotBlank(message = "idAmenity not blank")
        List<Integer> idAmenity) {
}
