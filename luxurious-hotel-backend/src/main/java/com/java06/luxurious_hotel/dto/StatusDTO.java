package com.java06.luxurious_hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDTO {
    List<PaymentStatusDTO> listPaymentStatus;
    List<PaymentMethodDTO> listPaymentMethod;
    List<BookingStatusDTO> listBookingStatus;
}
