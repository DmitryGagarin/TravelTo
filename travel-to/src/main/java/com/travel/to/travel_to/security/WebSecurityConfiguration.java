package com.travel.to.travel_to.security;

import com.travel.to.travel_to.configuration.WebConfig;
import com.travel.to.travel_to.security.jwt.JwtTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private final WebConfig webConfig;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WebSecurityConfiguration(
        WebConfig webConfig,
        UserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder
    ) {
        this.webConfig = webConfig;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
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
                    "/attraction/published",
                    // TODO: как то настроить роли для допуска
                    "/swagger-ui/**",
                    "/v3/**",
                    "/actuator/**")
                .permitAll()
                .anyRequest()
                .authenticated()
            )
            .addFilterBefore(
                new JwtTokenValidator(),
                BasicAuthenticationFilter.class
            );

        return http.build();
    }
}
