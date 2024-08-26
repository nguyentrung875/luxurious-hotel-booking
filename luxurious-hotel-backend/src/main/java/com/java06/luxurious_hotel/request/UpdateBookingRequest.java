package com.java06.luxurious_hotel.request;

public record UpdateBookingRequest(
        int idBooking,
        int idUser,
        String firstName,
        String lastName,
        String phone,
        String email,
        String checkInDate,
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
