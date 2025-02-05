package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import com.pgs.ecommerce.Ecommerce.application.UserService;
import com.pgs.ecommerce.Ecommerce.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller that manages user-related requests such as saving and retrieving user data.
 * The operations are delegated to the UserService to handle the actual business logic.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
@Tag(name = "User", description = "API for managing users")
@SecurityRequirement(name = "bearerAuth")  
public class UserController {

    private final UserService userService;

    /**
     * Saves a new user to the system.
     * 
     * @param user The user to be saved.
     * @return A ResponseEntity containing the saved user data.
     */
    @Operation(summary = "Create a new user", description = "Creates a new user and saves it to the system.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User saved successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request - invalid user data")
    })
    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        log.info("Received request to save user: {}", user);
        return ResponseEntity.ok(this.userService.save(user));
    }

    /**
     * Finds a user by their ID.
     * 
     * @param id The ID of the user to be retrieved.
     * @return A ResponseEntity with the user data if found, or a 404 response if not found.
     */
    @Operation(summary = "Find a user by ID", description = "Retrieves a user by their ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(
            @Parameter(description = "ID of the user to retrieve", required = true)
            @PathVariable Integer id) {
        log.info("Received request to find user with ID: {}", id);
        User user = this.userService.findById(id);
        if (user != null) {
            log.info("User found: {}", user);
            return ResponseEntity.ok(user);
        } else {
            log.warn("User with ID {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }
}
