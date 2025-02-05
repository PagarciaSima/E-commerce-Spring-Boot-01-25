package com.pgs.ecommerce.Ecommerce.application;

import java.util.NoSuchElementException;

import com.pgs.ecommerce.Ecommerce.domain.model.Category;
import com.pgs.ecommerce.Ecommerce.domain.port.ICategoryRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing categories in the system.
 */
@AllArgsConstructor
@Slf4j
public class CategoryService {
    private final ICategoryRepository iCategoryRepository;

    /**
     * Saves a new category to the database.
     *
     * @param category The category to be saved.
     * @return The saved category with its assigned ID.
     */
    public Category save(Category category) {
        log.info("Saving new category: {}", category);
        Category savedCategory = this.iCategoryRepository.save(category);
        log.info("Category saved successfully with ID: {}", savedCategory.getId());
        return savedCategory;
    }

    /**
     * Retrieves all stored categories.
     *
     * @return An iterable containing all categories.
     */
    public Iterable<Category> findAll() {
        log.info("Fetching all categories");
        Iterable<Category> categories = this.iCategoryRepository.findAll();
        log.info("Retrieved categories");
        return categories;
    }

    /**
     * Finds a category by its ID.
     *
     * @param id The ID of the category to find.
     * @return The found category.
     * @throws NoSuchElementException If the category does not exist.
     */
    public Category findById(Integer id) {
        log.info("Fetching category with ID: {}", id);
        Category category = iCategoryRepository.findById(id);
        if (category == null) {
            log.warn("Category with ID: {} not found", id);
            throw new NoSuchElementException("Category not found with id: " + id);
        }
        log.info("Category retrieved successfully: {}", category);
        return category;
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id The ID of the category to delete.
     * @throws NoSuchElementException If the category does not exist.
     */
    public void deleteById(Integer id) {
        log.info("Deleting category with ID: {}", id);
        this.findById(id);
        this.iCategoryRepository.deleteById(id);
        log.info("Category with ID: {} deleted successfully", id);
    }

    /**
     * Updates an existing category.
     *
     * @param id       The ID of the category to update.
     * @param category The category with updated data.
     * @return The updated category.
     * @throws NoSuchElementException If the category does not exist.
     */
    public Category update(Integer id, Category category) {
        log.info("Updating category with ID: {}", id);
        this.findById(id);
        category.setId(id);
        Category updatedCategory = iCategoryRepository.save(category);
        log.info("Category with ID: {} updated successfully", id);
        return updatedCategory;
    }
}
