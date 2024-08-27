package com.java06.luxurious_hotel.request;

public record UpdateBookingRequest(
        int idBooking,
        int idGuest,
        String checkInDate,
        String checkOutDate,
        int roomNumber,
        String rooms,
        int adultNumber,
        int childrenNumber,
        int idBookingStatus,
        int idPaymentStatus,
        int idPayment,
        double paidAmount,
        double total
){
}
