package com.java06.luxurious_hotel.service.imp;

import com.java06.luxurious_hotel.dto.BookingStatusDTO;
import com.java06.luxurious_hotel.dto.PaymentMethodDTO;
import com.java06.luxurious_hotel.dto.PaymentStatusDTO;
import com.java06.luxurious_hotel.dto.StatusDTO;
import com.java06.luxurious_hotel.repository.BookingStatusRepository;
import com.java06.luxurious_hotel.repository.PaymentMethodRepository;
import com.java06.luxurious_hotel.repository.PaymentStatusRepository;
import com.java06.luxurious_hotel.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImp implements StatusService {

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private BookingStatusRepository bookingStatusRepository;

    @Override
    public StatusDTO getAllStatus() {

        StatusDTO statusDTO = new StatusDTO();
        statusDTO.setListBookingStatus(bookingStatusRepository.findAll().stream().map(bookingStatus ->
                new BookingStatusDTO(bookingStatus.getId(), bookingStatus.getName())).toList());

        statusDTO.setListPaymentStatus(paymentStatusRepository.findAll().stream().map(paymentStatus ->
                new PaymentStatusDTO(paymentStatus.getId(), paymentStatus.getName())).toList());

        statusDTO.setListPaymentMethod(paymentMethodRepository.findAll().stream().map(paymentMethod ->
                new PaymentMethodDTO(paymentMethod.getId(), paymentMethod.getName())).toList());

        return statusDTO;
    }
}
