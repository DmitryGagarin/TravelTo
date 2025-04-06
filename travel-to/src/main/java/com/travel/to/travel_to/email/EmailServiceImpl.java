package com.travel.to.travel_to.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(
        JavaMailSender javaMailSender,
        SpringTemplateEngine templateEngine
    ) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    @NotNull
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
    @NotNull
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

    @Override
    public void sendAccountVerificationEmail(
        String email,
        String verificationUrl
    ) throws MessagingException {
        Context context = new Context();
        context.setVariable("verificationUrl", verificationUrl);

        // Render the email body using the Thymeleaf template
        String body = templateEngine.process("verification-email-template", context);

        // Create and send the email
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(email);
        helper.setSubject("Account Verification");
        helper.setText(body, true);  // The 'true' flag means it's HTML content

        // Send the email
        javaMailSender.send(mimeMessage);
    }

}
