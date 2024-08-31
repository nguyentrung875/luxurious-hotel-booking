package com.java06.luxurious_hotel.dto;

import lombok.Data;

@Data
public class GuestDTO {
    private int id;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String summary;
}
