package com.example.emailconsumer.consumer;

import com.example.emailconsumer.dto.BookingDTO;
import com.example.emailconsumer.service.EmailService;
import com.example.emailconsumer.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailProcessor {

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String emailFrom;

    @RabbitListener(queues = "confirm-booking-email-queue")
    public void sendConfirmBookingEmail(String data) throws MessagingException {
        try {
            emailService.sendConfirmBookingEmail(data);
        } catch (Exception e){
            System.out.println("Error send Confirm Email: " + e.getMessage());
        }
    }

    @RabbitListener(queues = "success-booking-email-queue")
    public void sendSuccessBookingEmail(String data) throws MessagingException {
        try {
            emailService.sendSuccessBookingEmail(data);
        } catch (Exception e){
            System.out.println("Error send Success Email: " + e.getMessage());
        }
    }
}
