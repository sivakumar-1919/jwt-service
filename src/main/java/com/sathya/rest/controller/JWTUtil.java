package com.sathya.rest.controller;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {

    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    
    public static String generateToken(String username) {
    	
    	Instant now = Instant.now();
    	
    	return Jwts.builder()
    			.setSubject(username)
    			.setIssuedAt(Date.from(now))
    			.setExpiration(Date.from(now.plus(30,ChronoUnit.MINUTES)))
    			.signWith(SECRET_KEY)
    			.compact();
    }

    
    

}
