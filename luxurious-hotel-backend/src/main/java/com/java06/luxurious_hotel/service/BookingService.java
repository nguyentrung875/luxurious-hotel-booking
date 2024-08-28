package com.java06.luxurious_hotel.service;

import com.java06.luxurious_hotel.dto.BookingDTO;
import com.java06.luxurious_hotel.request.AddBookingRequest;
import com.java06.luxurious_hotel.request.UpdateBookingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    List<BookingDTO> getAllBooking();
    void addNewBooking(AddBookingRequest request);
    void updateBooking(UpdateBookingRequest request);
    void deleteBooking(int idBooking);
}
