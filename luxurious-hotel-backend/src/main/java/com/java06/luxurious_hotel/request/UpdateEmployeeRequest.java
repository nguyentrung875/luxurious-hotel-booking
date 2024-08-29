package com.java06.luxurious_hotel.request;

import org.springframework.web.multipart.MultipartFile;

public record UpdateEmployeeRequest (int id,
                                     String firstname,
                                     String lastname,
                                     String dob,
                                     String phone,
                                     String email,
                                     String address,
                                     int IdRole,
                                     MultipartFile image,
                                     String summary) {
}
