package com.pgs.ecommerce.Ecommerce.application;

import java.util.NoSuchElementException;

import com.pgs.ecommerce.Ecommerce.domain.model.User;
import com.pgs.ecommerce.Ecommerce.domain.port.IUserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class UserService {

    private final IUserRepository userRepository;

    public User save(User user) {
        log.info("Saving new user: {}", user);
        User savedUser = this.userRepository.save(user);
        log.info("User saved successfully with ID: {}", savedUser.getId());
        return savedUser;
    }

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
    
    public User findByEmail(String email) {
    	return userRepository.findByEmail(email);
    }
}
