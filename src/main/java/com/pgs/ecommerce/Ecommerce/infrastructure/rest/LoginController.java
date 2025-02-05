package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.ecommerce.Ecommerce.infrastructure.dto.JWTClient;
import com.pgs.ecommerce.Ecommerce.infrastructure.dto.UserDTO;
import com.pgs.ecommerce.Ecommerce.infrastructure.jwt.JWTGenerator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The LoginController class handles user authentication requests.
 * It provides an endpoint for users to log in and receive a JWT upon successful authentication.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/security")
@Slf4j
@AllArgsConstructor
@Tag(name = "Login", description = "API for managing login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param dto The user's login credentials.
     * @return A ResponseEntity containing a JWTClient with the JWT token if authentication is successful,
     *         or an error message if authentication fails.
     */
    @Operation(summary = "User Login", description = "Authenticates the user and returns a JWT token upon successful authentication.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully logged in and JWT token returned."),
        @ApiResponse(responseCode = "401", description = "Invalid username or password."),
        @ApiResponse(responseCode = "500", description = "An internal error occurred.")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO dto) {
        try {
            log.info("Attempting login for user: {}", dto.username());

            // Authenticate the user
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            log.info("User '{}' logged in successfully", dto.username());
            log.info("User '{}' has the following roles: {}", 
                    dto.username(), 
                    SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString()
            );

            // Generate JWT token
            String token = jwtGenerator.getToken(dto.username());
            return ResponseEntity.ok(new JWTClient(token));

        } catch (BadCredentialsException e) {
            log.warn("Failed login attempt for user: {}", dto.username());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            log.error("Unexpected error during login: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
