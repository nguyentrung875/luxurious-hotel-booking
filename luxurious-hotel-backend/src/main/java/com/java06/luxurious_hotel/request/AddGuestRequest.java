package com.java06.luxurious_hotel.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AddGuestRequest(
        @NotNull(message = "Họ tên không được để trống")
        @Pattern(regexp = "^[a-zA-ZÀ-ỹ\\s]+$", message = "Full name must contain only letters and spaces")
        String fullName,

        @NotNull(message = "phone không được null")
        @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
        String phone,

        @Email(message = "email chưa đúng định dạng")
        String email,

        String address,

        String summary) {
}
