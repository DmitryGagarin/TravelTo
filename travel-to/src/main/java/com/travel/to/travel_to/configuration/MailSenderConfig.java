package com.travel.to.travel_to.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(1025);  // Make sure you're using the correct port

        // Explicitly disable STARTTLS and SSL
//        mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", "false");
//        mailSender.getJavaMailProperties().put("mail.smtp.starttls.required", "false");
//        mailSender.getJavaMailProperties().put("mail.smtp.ssl.enable", "false");
//        mailSender.getJavaMailProperties().put("mail.smtp.auth", "false");

        return mailSender;
    }

}
