package com.pgs.ecommerce.Ecommerce.domain.port;

import com.pgs.ecommerce.Ecommerce.domain.model.User;

public interface IUserRepository {
    User save (User user);
    User findById (Integer id);
    User findByEmail (String email);
}
