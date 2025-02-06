package com.pgs.ecommerce.Ecommerce.infrastructure.adapter;

import com.pgs.ecommerce.Ecommerce.domain.model.User;
import com.pgs.ecommerce.Ecommerce.domain.port.IUserRepository;
import com.pgs.ecommerce.Ecommerce.infrastructure.entity.UserEntity;
import com.pgs.ecommerce.Ecommerce.infrastructure.mapper.IUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

/**
 * Implementation of the {@link IUserRepository} interface that interacts with the database
 * to perform CRUD operations on {@link User} entities.
 */
@Repository
@AllArgsConstructor
public class UserCrudRepositoryImpl implements IUserRepository {

    private final IUserCrudRepository iUserCrudRepository;
    private final IUserMapper IUserMapper;

    /**
     * Saves a {@link User} entity to the database.
     *
     * @param user the {@link User} entity to be saved
     * @return the saved {@link User} entity
     */
    @Override
    public User save(User user) {
        return IUserMapper.toUser(iUserCrudRepository.save(IUserMapper.toUserEntity(user)));
    }

    /**
     * Retrieves a {@link User} entity by its ID.
     *
     * @param id the ID of the {@link User} entity to retrieve
     * @return the retrieved {@link User} entity
     * @throws NoSuchElementException if no {@link User} entity with the specified ID is found
     */
    @Override
    public User findById(Integer id) {
        return IUserMapper.toUser(iUserCrudRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User ID " + id + " not found")
        ));
    }

    /**
     * Retrieves a {@link User} entity by its email address.
     *
     * @param email the email address of the {@link User} entity to retrieve
     * @return the retrieved {@link User} entity or null if not found
     */
    @Override
    public User findByEmail(String email) {
        UserEntity userEntity = iUserCrudRepository.findByEmail(email)
                .orElse(null);
        return IUserMapper.toUser(userEntity);
    }
}
