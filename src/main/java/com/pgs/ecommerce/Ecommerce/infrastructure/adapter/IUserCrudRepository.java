package com.pgs.ecommerce.Ecommerce.infrastructure.adapter;

import com.pgs.ecommerce.Ecommerce.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserCrudRepository extends JpaRepository<UserEntity, Integer> {
}
