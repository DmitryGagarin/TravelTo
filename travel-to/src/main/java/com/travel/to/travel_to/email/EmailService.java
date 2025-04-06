package com.travel.to.travel_to.email;

import jakarta.mail.MessagingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;

public interface EmailService {

    @NotNull
    SimpleMailMessage constructSimpleEmail(
        @NotNull String address,
        @NotNull String subject,
        @NotNull String message
    );

    @NotNull
    MimeMailMessage constructMimeEmail(
        @NotNull String address,
        @NotNull String subject,
        @NotNull String message
    );

    void sendSimpleEmail(
        @NotNull String address,
        @NotNull String subject,
        @NotNull String message
    );

    void sendMimeEmail(

    );

    void sendAccountVerificationEmail(
        String email,
        String verificationUrl
    ) throws MessagingException;
}
