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

/**
 * Controller responsible for user registration operations.
 * It handles the registration process including validating email uniqueness,
 * password encoding, and delegating the registration process to the service layer.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/security")
@AllArgsConstructor
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Registers a new user by validating the email, encoding the password, 
     * and then saving the user to the system.
     * 
     * @param user The user to be registered, containing the user's email and password.
     * @return A ResponseEntity containing the registered user object or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        log.info("Request to register new user with email: {}", user.getEmail());

        // Check if the email is already in use
        if (userService.findByEmail(user.getEmail()) != null) {
            log.warn("Attempt to register with an existing email: {}", user.getEmail());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already in use");
        }

        // Encode the password before saving the user
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Register the user using the service layer
        User registeredUser = registrationService.register(user);
        log.info("User registered successfully with email: {}", registeredUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}
