package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pgs.ecommerce.Ecommerce.application.CategoryService;
import com.pgs.ecommerce.Ecommerce.domain.model.Category;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing categories in the e-commerce application.
 * Provides endpoints for creating, updating, retrieving, and deleting categories.
 * <p>
 * Base URL: {@code /api/v1/admin/categories}
 * </p>
 */
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/admin/categories")
@AllArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Creates a new category.
     *
     * @param category the category to be created
     * @return a {@link ResponseEntity} containing the created category and HTTP status {@code 201 (Created)}
     */
    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {
        log.info("Saving new category: {}", category);
        Category savedCategory = this.categoryService.save(category);
        log.info("Category saved successfully with id: {}", savedCategory.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    /**
     * Updates an existing category.
     *
     * @param id       the ID of the category to be updated
     * @param category the updated category data
     * @return a {@link ResponseEntity} containing the updated category and HTTP status {@code 200 (OK)}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category category) {
        log.info("Updating category with id: {}", id);
        Category updatedCategory = this.categoryService.update(id, category);
        log.info("Category updated successfully: {}", updatedCategory);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Retrieves all categories.
     *
     * @return a {@link ResponseEntity} containing a list of all categories and HTTP status {@code 200 (OK)}
     */
    @GetMapping
    public ResponseEntity<Iterable<Category>> findAll() {
        log.info("Fetching all categories...");
        Iterable<Category> categories = this.categoryService.findAll();
        log.info("Fetched {} categories", ((Collection<?>) categories).size());
        return ResponseEntity.ok(categories);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to be retrieved
     * @return a {@link ResponseEntity} containing the found category and HTTP status {@code 200 (OK)}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Integer id) {
        log.info("Fetching category with id: {}", id);
        Category category = this.categoryService.findById(id);
        log.info("Category found: {}", category);
        return ResponseEntity.ok(category);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to be deleted
     * @return a {@link ResponseEntity} with HTTP status {@code 204 (No Content)}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        log.warn("Deleting category with id: {}", id);
        this.categoryService.deleteById(id);
        log.warn("Category deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
