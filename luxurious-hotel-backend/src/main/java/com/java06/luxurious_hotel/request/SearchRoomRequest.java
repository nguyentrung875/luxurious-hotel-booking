package com.java06.luxurious_hotel.request;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record SearchRoomRequest(

        @NotNull(message = "checkIn not null")
        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Incorrect date format yyyy-MM-dd")
        String checkIn,


        @NotNull(message = "checkOut not null")
        @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$", message = "Incorrect date format yyyy-MM-dd")
        String checkOut,


        @Min(value = 1,message = "The number of adults must be at least 1")
        int adultNumber,

        int childrenNumber) {
}
