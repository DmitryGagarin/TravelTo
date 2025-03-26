package com.travel.to.travel_to.security;

import com.travel.to.travel_to.configuration.WebConfig;
import com.travel.to.travel_to.security.jwt.JwtTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public WebSecurityConfiguration(
        WebConfig webConfig
    ) {
        this.webConfig = webConfig;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
        final HttpSecurity http
    ) throws Exception {
        http
            .cors(cors -> cors.configurationSource(webConfig.corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(management ->
                    management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(
                    "/",
                    "/signin/**",
                    "/signup/**",
                    "/logout",
                    "/attraction")
                .permitAll()
                .anyRequest()
                .authenticated()
            )
            .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class);

        return http.build();
    }
}
