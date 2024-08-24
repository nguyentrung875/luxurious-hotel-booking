package com.java06.luxurious_hotel.service;

import com.java06.luxurious_hotel.payload.request.AddBookingRequest;
import jdk.jfr.Frequency;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface BookingService {
    void addNewBooking(AddBookingRequest request);
}
