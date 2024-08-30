package com.java06.luxurious_hotel.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record AddBookingRequest (
        @NotNull(message = "Không được null")
        @NotEmpty(message = "Không được rong")
        String firstName,
        String lastName,

        String phone,

        @Email(message = "Vui lòng nhập đúng định dạng email")
        String email,

        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "date format should be yyyy-MM-dd")
        String checkInDate,

        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "date format should be yyyy-MM-dd")
        String checkOutDate,
        int roomNumber,
        String rooms,
        int idGuest,
        int adultNumber,
        int childrenNumber,
        int idPaymentStatus,
        int idPayment,
        double paidAmount,
        double total
){
}
