package com.java06.luxurious_hotel.request;

import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record AddRoomtypeRequest(
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

        @NotNull(message = "images not null")
        @NotBlank(message = "images not blank")
        List<MultipartFile> images,
        List<Integer> idAmenity
        ) {
}
