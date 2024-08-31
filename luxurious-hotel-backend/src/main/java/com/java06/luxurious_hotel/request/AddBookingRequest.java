package com.java06.luxurious_hotel.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AddBookingRequest (
        @NotNull(message = "First name not null")
        @NotBlank(message = "First name not blank")
        String firstName,

        @NotNull(message = "Last name not null")
        @NotBlank(message = "Last name not blank")
        String lastName,

        @NotNull(message = "Phone not null")
        @NotBlank(message = "Phone not blank")
        String phone,

        @Email(message = "Incorrect email format")
        String email,

        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Incorrect date format yyyy-MM-dd")
        String checkInDate,

        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Incorrect date format yyyy-MM-dd")
        String checkOutDate,
        int roomNumber,

        @NotNull(message = "Rooms not null")
        @NotBlank(message = "Rooms not null")
        String rooms,

        @NotNull(message = "adultNumber not null")
        int adultNumber,

        int childrenNumber,

        int idPaymentStatus,

        int idPayment,

        double paidAmount,

        double total
){
}
