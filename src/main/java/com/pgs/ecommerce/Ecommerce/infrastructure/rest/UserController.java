package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import com.pgs.ecommerce.Ecommerce.application.UserService;
import com.pgs.ecommerce.Ecommerce.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that manages user-related requests such as saving and retrieving user data.
 * The operations are delegated to the UserService to handle the actual business logic.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Saves a new user to the system.
     * 
     * @param user The user to be saved.
     * @return A ResponseEntity containing the saved user data.
     */
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
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {
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
