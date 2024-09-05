package com.java06.luxurious_hotel.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record UpdateBookingRequest(
        @NotNull(message = "Booking Id not null")
        int idBooking,

        int idGuest,

        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Incorrect date format yyyy-MM-dd")
        String checkInDate,

        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Incorrect date format yyyy-MM-dd")
        String checkOutDate,

        int roomNumber,

        @NotNull(message = "Rooms not null")
        List<String> rooms,

        @NotNull(message = "adultNumber not null")
        int adultNumber,

        int childrenNumber,

        int idBookingStatus,

        int idPaymentStatus,

        int idPayment,

        double paidAmount,

        double total
){
}
