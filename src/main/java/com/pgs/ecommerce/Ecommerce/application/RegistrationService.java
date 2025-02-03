package com.pgs.ecommerce.Ecommerce.application;

import com.pgs.ecommerce.Ecommerce.domain.model.User;
import com.pgs.ecommerce.Ecommerce.domain.port.IUserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegistrationService {

	private final IUserRepository iUserRepository;
	
	public User register(User user) {
		return iUserRepository.save(user);
	}
}
