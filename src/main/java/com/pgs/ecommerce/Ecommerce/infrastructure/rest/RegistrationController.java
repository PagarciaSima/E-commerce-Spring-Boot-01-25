package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.ecommerce.Ecommerce.application.RegistrationService;
import com.pgs.ecommerce.Ecommerce.domain.model.User;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/security")
@AllArgsConstructor
@Slf4j
public class RegistrationController {

	private final RegistrationService registrationService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    log.info("Request to register new user with email: {}", user.getEmail());
	    User registeredUser = registrationService.register(user);
	    log.info("User registered successfully with email: {}", registeredUser.getEmail());
	    return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
	}

	
}
