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
import com.pgs.ecommerce.Ecommerce.application.UserService;
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
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody User user) {
	    log.info("Request to register new user with email: {}", user.getEmail());

	    if (userService.findByEmail(user.getEmail()) != null ) {
	        log.warn("Attempt to register with an existing email: {}", user.getEmail());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already in use");
	    }

	    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

	    User registeredUser = registrationService.register(user);
	    log.info("User registered successfully with email: {}", registeredUser.getEmail());

	    return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
	}

	
}
