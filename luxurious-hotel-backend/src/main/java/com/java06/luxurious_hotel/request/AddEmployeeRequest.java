package com.java06.luxurious_hotel.request;

import org.springframework.web.multipart.MultipartFile;

public record AddEmployeeRequest(String firstName,
                                 String username,
                                 String password,
                                 int Idrole,
                                 String lastName,
                                 String dob,
                                 String phone,
                                 String email,
                                 String address,
                                 String summary,
                                 MultipartFile image) {
}
