 package com.sathya.rest.controller;

import java.io.IOException;
import java.security.Key;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter{

	static final Key SECRET_KEY = JWTUtil.SECRET_KEY; 
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			                         HttpServletResponse response, 
			                          FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String authorizationHeader = request.getHeader("Authorization");
			 
			if ((authorizationHeader!=null)&&(authorizationHeader.startsWith("Bearer ")) ){
				 
				String token = authorizationHeader.substring(7);
				
				Claims claims = Jwts.parserBuilder()
						         .setSigningKey(SECRET_KEY)
						         .build()
						         .parseClaimsJws(token)	
						         .getBody();
				
				if (claims!=null) {
					String username = claims.getSubject();
					
					 UsernamePasswordAuthenticationToken authToken =
	                            new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
	                    SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				
			}
			
			
			
			
		} catch (JwtException | IllegalArgumentException e) {
			 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.setContentType("application/json");
	            response.getWriter().write("{\"error\": \"Invalid or expired JWT token by Ratna sir\"}");
	            return;
			
		}
		filterChain.doFilter(request, response);	  
		
	}

	
}
