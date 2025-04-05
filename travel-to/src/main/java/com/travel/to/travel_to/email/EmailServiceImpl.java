package com.travel.to.travel_to.email;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public SimpleMailMessage constructSimpleEmail(
        @NotNull String address,
        @NotNull String subject,
        @NotNull String message
    ) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(address);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);
        return simpleMailMessage;
    }

    @Override
    public MimeMailMessage constructMimeEmail(
        @NotNull String address,
        @NotNull String subject,
        @NotNull String message
    ) {
        return null;
    }

    @Override
    public void sendSimpleEmail(
        @NotNull String address,
        @NotNull String subject,
        @NotNull String message
    ) {
        javaMailSender.send(constructSimpleEmail(address, subject, message));
    }

    @Override
    public void sendMimeEmail() {

    }

}
