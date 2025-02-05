package com.pgs.ecommerce.Ecommerce.application;

import com.pgs.ecommerce.Ecommerce.domain.model.User;
import com.pgs.ecommerce.Ecommerce.domain.port.IUserRepository;

import lombok.AllArgsConstructor;

/**
 * Service responsible for handling user registration.
 */
@AllArgsConstructor
public class RegistrationService {

    private final IUserRepository iUserRepository;

    /**
     * Registers a new user in the system.
     *
     * @param user The user to be registered.
     * @return The saved user with the assigned ID.
     */
    public User register(User user) {
        return iUserRepository.save(user);
    }
}
