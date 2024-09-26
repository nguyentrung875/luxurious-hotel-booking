package com.example.emailconsumer.service;

import com.example.emailconsumer.request.AddBookingRequest;
import com.example.emailconsumer.utils.JwtUtils;
import com.google.gson.Gson;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Autowired
    private JwtUtils jwtUtils;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String emailFrom;

    public String sendConfirmBookingEmail(String bookingJson) throws MessagingException {
        Gson gson = new Gson();
        AddBookingRequest booking = gson.fromJson(bookingJson, AddBookingRequest.class);

        String confirmToken = jwtUtils.generateConfirmBookingToken(booking.idBooking());

        System.out.println("Email sending...");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(emailFrom);

        helper.setTo(booking.email());

        String roomNo = booking.roomName().stream().collect(Collectors.joining(", "));

        String content = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Hotel Booking Confirmation</title>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f7f7f7;\">\n" +
                "    <div\n" +
                "        style=\"width: 100%; max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);\">\n" +
                "\n" +
                "        <!-- Header -->\n" +
                "        <h2 style=\"text-align: center; color: #333;\">Hotel Booking Confirmation</h2>\n" +
                "\n" +
                "        <!-- Greeting -->\n" +
                "        <p style=\"color: #333; font-size: 16px;\">Dear <strong>"+booking.firstName()+ " " + booking.lastName() + "</strong>,</p>\n" +
                "        <p style=\"color: #333; font-size: 16px;\">Thank you for choosing to stay with us! Below are the details of your\n" +
                "            booking:</p>\n" +
                "\n" +
                "        <!-- Additional Information -->\n" +
                "        <p style=\"color: #333; font-size: 16px; margin-top: 20px;\">\n" +
                "            <a href=\"http://127.0.0.1:5500/luxurious-home/booking-history.html?conf="+confirmToken+"\">Please click here to confirm your booking!</a>\n" +
                "        </p>\n" +
                "        <!-- Booking Details -->\n" +
                "        <table style=\"width: 100%; border-collapse: collapse; margin-top: 20px;\">\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #555;\">Phone number:</td>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #333;\">"+booking.phone() +"</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #555;\">Check-in Date:</td>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #333;\">"+booking.checkInDate()+"</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #555;\">Check-out Date:</td>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #333;\">"+booking.checkOutDate()+"</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #555;\">Number of adult</td>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #333;\">"+booking.adultNumber()+"</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #555;\">Number of children</td>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #333;\">"+booking.childrenNumber()+"</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #555;\">Room Number:</td>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #333;\">"+roomNo+"</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #555;\">Total Amount:</td>\n" +
                "                <td style=\"padding: 10px; border: 1px solid #ddd; font-size: 14px; color: #333;\">"+booking.total()+"</td>\n" +
                "            </tr>\n" +
                "        </table>\n" +

                "\n" +
                "        <!-- Additional Information -->\n" +
                "        <p style=\"color: #333; font-size: 16px; margin-top: 20px;\">\n" +
                "            We look forward to welcoming you. If you have any questions, feel free to contact us.\n" +
                "        </p>\n" +
                "\n" +
                "        <!-- Contact Information -->\n" +
                "        <p style=\"color: #333; font-size: 14px; margin-top: 20px;\">\n" +
                "            Best Regards,<br>\n" +
                "            <strong>Luxurious Hotel</strong><br>\n" +
                "            Phone: 1900 0091 <br>\n" +
                "        </p>\n" +
                "\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>";

        helper.setSubject("Confirm your booking - Luxurious Hotel");
        helper.setText(content, true);

        mailSender.send(message);
        System.out.println("Confirmation email has been sent to " + booking.email());
        return "Confirmation email has been sent to " + booking.email();
    }

}
