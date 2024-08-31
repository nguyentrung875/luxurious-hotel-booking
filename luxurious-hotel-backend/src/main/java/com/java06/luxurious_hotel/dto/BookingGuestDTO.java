package com.java06.luxurious_hotel.dto;

import com.java06.luxurious_hotel.dto.coverdto.RoomTypeDTO;
import com.java06.luxurious_hotel.entity.UserEntity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookingGuestDTO {
    private GuestDTO guestDTO;

    private int idBooking;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String paymentStatus;
    private String paymentMethod;
    private List<RoomTypeDTO> roomTypeDTO;
    private int member;
    private int quantilyRoom;
}
