package com.travel.to.travel_to.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Travel to api",
        description = "Documentation of travel to api",
        version = "1.0.0",
        contact = @Contact(
            name = "Dmitry Gagarin",
            email = "gagriu@yandex.ru",
            url = "https://t.me/gagarinonelove"
        )
    )
)
@Configuration
public class SwaggerConfig {
}
