package com.pgs.ecommerce.Ecommerce.domain.port;

import com.pgs.ecommerce.Ecommerce.domain.model.Category;

public interface ICategoryRepository {
    Category save (Category category);
    Iterable<Category> findAll();
    Category findById (Integer id);
    void deleteById (Integer id);
}
