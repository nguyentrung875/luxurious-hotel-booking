package com.example.emailconsumer.consumer;

import com.example.emailconsumer.request.AddBookingRequest;
import com.example.emailconsumer.service.EmailService;
import com.example.emailconsumer.utils.JwtUtils;
import com.google.gson.Gson;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmailProcessor {

    @Autowired
    private EmailService emailService;


    @Autowired
    private JwtUtils jwtUtils;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String emailFrom;

    @RabbitListener(queues = "confirm-email-queue")
    public void sendConfirmEmail(String data) throws MessagingException {
        try {
            emailService.sendConfirmBookingEmail(data);
        } catch (Exception e){
            System.out.println("Error send Email: " + e.getMessage());
        }
    }
}
