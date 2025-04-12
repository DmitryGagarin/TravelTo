package com.travel.to.travel_to.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
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
    public void sendAccountVerificationEmail(
        @NotNull String email,
        @NotNull String verificationUrl
    ) throws MessagingException {
        Context context = new Context();
        context.setVariable(EmailVariables.TITLE, EmailTitles.ACCOUNT_VERIFICATION);
        context.setVariable(EmailVariables.VERIFICATION_URL, verificationUrl);

        String body = templateEngine.process(EmailTemplateNames.ACCOUNT_VERIFICATION_TEMPLATE, context);

        MimeMessage mimeMessage = constuctMimeMessage(
            email,
            EmailSubjects.ACCOUNT_VERIFICATION,
            body
        );

        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendPasswordResetEmail(
        @NotNull String email,
        @NotNull String resetUrl
    ) throws MessagingException {
        Context context = new Context();
        context.setVariable(EmailVariables.TITLE, EmailTitles.RESET_PASSWORD);
        context.setVariable(EmailVariables.RESET_PASSWORD_URL, resetUrl);
        String body = templateEngine.process(EmailTemplateNames.RESET_PASSWORD_TEMPLATE, context);

        MimeMessage mimeMessage = constuctMimeMessage(
            email,
            EmailSubjects.PASSWORD_RESET,
            body
        );

        javaMailSender.send(mimeMessage);
    }

    @NotNull
    public MimeMessage constuctMimeMessage(
        @NotNull String email,
        @NotNull String subject,
        @NotNull String body
    ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(body, true);
        return mimeMessage;
    }
}
