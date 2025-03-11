package com.travel.to.travel_to.security;

import com.travel.to.travel_to.configuration.WebConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final WebConfig webConfig;

    public WebSecurityConfiguration(WebConfig webConfig) {
        this.webConfig = webConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .sessionManagement(management ->
                    management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/singin", "/signup", "/", "/logout").permitAll()
                .anyRequest()
                .authenticated()
            )
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(webConfig.corsConfigurationSource()));

        return http.build();
    }
}
