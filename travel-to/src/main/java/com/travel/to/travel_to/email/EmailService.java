package com.travel.to.travel_to.email;

import jakarta.validation.constraints.NotNull;
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

    @NotNull
    void sendSimpleEmail(
        @NotNull String address,
        @NotNull String subject,
        @NotNull String message
    );

    @NotNull
    void sendMimeEmail(

    );
}
