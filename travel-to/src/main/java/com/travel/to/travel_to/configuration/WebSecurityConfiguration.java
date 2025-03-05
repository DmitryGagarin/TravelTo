package com.travel.to.travel_to.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/singin", "/signup", "/", "/logout")
                .permitAll()
                .anyRequest()
                .authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/signin")
                .permitAll()
            )
            .logout(logout -> logout.permitAll());
        return http.build();
    }
}
