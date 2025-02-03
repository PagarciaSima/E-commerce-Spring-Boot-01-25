package com.pgs.ecommerce.Ecommerce.infrastructure.adapter;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgs.ecommerce.Ecommerce.infrastructure.entity.UserEntity;

public interface IUserCrudRepository extends JpaRepository<UserEntity, Integer> {
	Optional<UserEntity> findByEmail(String email);
}
