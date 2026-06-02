package com.sathya.rest.controller;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sathya.rest.dto.LoginDTO;
import com.sathya.rest.entity.Registration;
import com.sathya.rest.repository.RegistrationRepository;

@CrossOrigin(origins = {"http://localhost:5173" , "https://react-spicy-food.vercel.app"})
@RestController
public class JWTController {

    @Autowired
    private RegistrationRepository repository;

    @PostMapping("/api/auth/register")
    public ResponseEntity<String> register(
            @RequestBody Registration registration) {

        if (repository.existsByEmail(
                registration.getEmail())) {

            return ResponseEntity
                    .badRequest()
                    .body("Email already exists");
        }

        repository.save(registration);

        return ResponseEntity.ok(
                "Registration Successful");
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginDTO> login(
            @RequestBody LoginDTO loginDTO) {

        Optional<Registration> user =
                repository.findByEmail(loginDTO.getEmail());

        if (user.isPresent()
                && user.get().getPassword()
                        .equals(loginDTO.getPassword())) {

            String token =
                    JWTUtil.generateToken(loginDTO.getEmail());

            LoginDTO response = new LoginDTO();

            response.setToken(token);
            response.setName(user.get().getName());
            response.setEmail(user.get().getEmail());

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .build();
    }
}
