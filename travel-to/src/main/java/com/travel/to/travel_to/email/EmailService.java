package com.travel.to.travel_to.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;

public interface EmailService {

    void sendAccountVerificationEmail(
        String email,
        String verificationUrl
    ) throws MessagingException;

    void sendPasswordResetEmail(
        String email,
        String resetUrl
    ) throws MessagingException;

    @NotNull
    MimeMessage constuctMimeMessage(
        @NotNull String email,
        @NotNull String subject,
        @NotNull String body
    ) throws MessagingException;

}
