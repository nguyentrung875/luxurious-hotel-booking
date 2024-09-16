package com.java06.luxurious_hotel.controller;

import com.java06.luxurious_hotel.response.BaseResponse;
import com.java06.luxurious_hotel.service.EmailService;
import com.java06.luxurious_hotel.service.imp.EmailServiceImp;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailServiceImp emailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestParam String recipients, @RequestParam String subject,
                                       @RequestParam String content, @RequestParam(required = false) MultipartFile[] files) throws MessagingException {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage(emailService.sendEmail(recipients, subject, content, files));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
