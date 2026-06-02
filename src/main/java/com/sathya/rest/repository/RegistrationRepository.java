package com.sathya.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sathya.rest.entity.Registration;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

	boolean existsByEmail(String email);

	Optional<Registration> findByEmail(String email);

}
