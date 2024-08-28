package com.java06.luxurious_hotel.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class BookingDTO {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String paymentMethod;
    private String paymentStatus;
    private int adultNo;
    private int childrenNo;
    private HashMap<String, List<String>> roomNo; //Định dạng: RoomType:  101, 102, ...
    private String bookingStatus;
    private double paidAmount;
    private double total;
}
