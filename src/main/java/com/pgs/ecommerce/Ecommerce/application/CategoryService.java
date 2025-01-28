package com.pgs.ecommerce.Ecommerce.application;

import java.util.NoSuchElementException;

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
    	Category category = iCategoryRepository.findById(id);
    	if (null == category)
            throw new NoSuchElementException("Category not found with id: " + id); 
        return this.iCategoryRepository.findById(id);
    }

    public void deleteById (Integer id) {
    	this.findById(id);
        this.iCategoryRepository.deleteById(id);
    }

	public Category update(Integer id, Category category) {
        this.findById(id);
        category.setId(id);
        return iCategoryRepository.save(category);
	}

}
