package com.pgs.ecommerce.Ecommerce.application;

import java.util.NoSuchElementException;

import com.pgs.ecommerce.Ecommerce.domain.model.User;
import com.pgs.ecommerce.Ecommerce.domain.port.IUserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing user-related operations.
 */
@AllArgsConstructor
@Slf4j
public class UserService {

    private final IUserRepository userRepository;

    /**
     * Saves a new user in the repository.
     *
     * @param user The user to be saved.
     * @return The saved user with assigned ID.
     */
    public User save(User user) {
        log.info("Saving new user: {}", user);
        User savedUser = this.userRepository.save(user);
        log.info("User saved successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

    /**
     * Finds a user by their ID.
     * Throws an exception if the user is not found.
     *
     * @param id The ID of the user to find.
     * @return The found user.
     * @throws NoSuchElementException If no user is found with the given ID.
     */
    public User findById(Integer id) {
        log.info("Fetching user with ID: {}", id);
        User user = this.userRepository.findById(id);
        if (user == null) {
            log.warn("User with ID {} not found", id);
            throw new NoSuchElementException(String.format("User with ID %s not found", id));
        }
        log.info("User retrieved successfully: {}", user);
        return user;
    }
    
    /**
     * Finds a user by their email.
     *
     * @param email The email of the user to find.
     * @return The user with the given email.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
