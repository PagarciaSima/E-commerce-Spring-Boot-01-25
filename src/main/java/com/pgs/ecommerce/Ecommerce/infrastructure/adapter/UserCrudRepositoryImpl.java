package com.pgs.ecommerce.Ecommerce.infrastructure.adapter;

import com.pgs.ecommerce.Ecommerce.domain.model.User;
import com.pgs.ecommerce.Ecommerce.domain.port.IUserRepository;
import com.pgs.ecommerce.Ecommerce.infrastructure.entity.UserEntity;
import com.pgs.ecommerce.Ecommerce.infrastructure.mapper.IUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class UserCrudRepositoryImpl implements IUserRepository {

    private final IUserCrudRepository iUserCrudRepository;
    private final IUserMapper IUserMapper;

    @Override
    public User save(User user) {
        return IUserMapper.toUser(iUserCrudRepository.save(IUserMapper.toUserEntity(user)));
    }

    @Override
    public User findById(Integer id) {
        return IUserMapper.toUser(iUserCrudRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User ID " + id + " not found")
        ));
    }

    @Override
    public User findByEmail(String email) {
        UserEntity userEntity = iUserCrudRepository.findByEmail(email)
        		.orElseThrow(() -> new NoSuchElementException("No user found with email " + email));
        return IUserMapper.toUser(userEntity);
    }
}
