package com.java06.luxurious_hotel.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingGuestDTO {
    private int idGuest;
    private String fullName;
    private String phone;
    private String email;
    private String address;

    private int idBooking;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String paymentStatus;
    private String paymentMethod;
    private List<String> roomType;
    private List<String> roomName;
    private int member;
    private int quantilyRoom;
}
