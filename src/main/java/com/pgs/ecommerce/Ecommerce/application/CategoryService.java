package com.pgs.ecommerce.Ecommerce.application;

import com.pgs.ecommerce.Ecommerce.domain.model.Category;
import com.pgs.ecommerce.Ecommerce.domain.port.ICategoryRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CategoryService {
    private final ICategoryRepository iCategoryRepository;

    public Category save (Category category) {
        return this.iCategoryRepository.save(category);
    }

    public Iterable<Category> findAll() {
        return this.iCategoryRepository.findAll();
    }

    public Category findById(Integer id) {
        return this.iCategoryRepository.findById(id);
    }

    public void deleteById (Integer id) {
        this.iCategoryRepository.deleteById(id);
    }

}
