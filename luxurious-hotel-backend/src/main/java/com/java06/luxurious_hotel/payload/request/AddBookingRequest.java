package com.java06.luxurious_hotel.payload.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AddBookingRequest (
        String checkInDate,
        String checkOutDate,
        int roomNumber,
        int idGuest,
        int adultNumber,
        int childrenNumber,
        int idPaymentStatus,
        int idPayment,
        double paidAmount,
        double total
){
}
