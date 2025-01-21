package com.pgs.ecommerce.Ecommerce.application;

import com.pgs.ecommerce.Ecommerce.domain.model.User;
import com.pgs.ecommerce.Ecommerce.domain.port.IUserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    public User save (User user) {
        return this.userRepository.save(user);
    }

    public User findById (Integer id) {
        return this.userRepository.findById(id);
    }
}
