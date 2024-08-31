package com.java06.luxurious_hotel.service;

import com.java06.luxurious_hotel.dto.BookingGuestDTO;
import com.java06.luxurious_hotel.request.AddBookingRequest;
import com.java06.luxurious_hotel.request.UpdateBookingRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookingService {
    void addNewBooking(AddBookingRequest request);
    void editBooking(UpdateBookingRequest request);
    boolean deleteBooking(int idBooking);
    List<BookingGuestDTO> getListBooking(int idGuest);
}
