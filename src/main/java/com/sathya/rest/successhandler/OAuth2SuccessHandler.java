package com.sathya.rest.successhandler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sathya.rest.controller.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	

    @Value("${app.frontend.url}")
    private String frontendUrl;
    
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        
        String email = user.getAttribute("email");
        String name = user.getAttribute("name");
        
     // GitHub fallback
        if (name == null) {
            name = user.getAttribute("login");
        }

        if (email == null) {
            email = " Email Not Available" ;
        }
        
        String token = JWTUtil.generateToken(email);
        response.sendRedirect(
                frontendUrl + "/oauth-success?token=" + token +
                "&email=" + email +
                "&name=" + name
        );
    }
}