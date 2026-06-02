package com.sathya.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sathya.rest.successhandler.OAuth2SuccessHandler;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    
    @Autowired
    OAuth2SuccessHandler auth2SuccessHandler ; 

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                    .requestMatchers(
                            "/api/auth/register",
                            "/api/auth/login")
                    .permitAll()

                    .anyRequest()
                    .authenticated())

            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class)
        
        // ✅ OAuth2 LOGIN
        .oauth2Login(oauth -> oauth
                .successHandler(auth2SuccessHandler)
        );

        return http.build();
    }
}