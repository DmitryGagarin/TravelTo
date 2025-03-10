package com.travel.to.travel_to.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry
                    .addMapping("/**")
                    .allowedOrigins("http://localhost:3000")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowCredentials(true);
            }
        };
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600);

        // Log the loaded messages for debugging
        String[] propertyFiles = {"messages/messages.properties", "messages/messages_en_US.properties"};
        for (String file : propertyFiles) {
            System.out.println(file);
            Properties properties = new Properties();
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file)) {
                if (inputStream != null) {
                    properties.load(inputStream);
                    System.out.println("Loaded properties from " + file + ":");
                    properties.forEach((key, value) -> System.out.println(key + " = 1234" + value));
                } else {
                    System.err.println(file + " file not found!");
                }
            } catch (IOException e) {
                System.err.println("Failed to load " + file + ": " + e.getMessage());
            }
        }

        return messageSource;
    }

    @Bean
    @DependsOn("messageSource")
    public LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        System.out.println("LocalValidatorFactoryBean initialized with message source: " + bean.getValidationPropertyMap());
        System.out.println("MessageSource class: " + messageSource.getClass().getName());
        return bean;
    }
}

