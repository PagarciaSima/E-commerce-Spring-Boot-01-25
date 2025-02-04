package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.ecommerce.Ecommerce.application.CategoryService;
import com.pgs.ecommerce.Ecommerce.domain.model.Category;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/admin/categories")
@AllArgsConstructor
@Slf4j

// http://localhost:8085/api/v1/admin/categories
public class CategoryController {

    private final CategoryService categoryService;
    
    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {
        log.info("Saving new category: {}", category);
        Category savedCategory = this.categoryService.save(category);
        log.info("Category saved successfully with id: {}", savedCategory.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Integer id, @RequestBody Category category) {
        log.info("Updating category with id: {}", id);
        Category updatedCategory = this.categoryService.update(id, category);
        log.info("Category updated successfully: {}", updatedCategory);
        return ResponseEntity.ok(updatedCategory);
    }
    
    @GetMapping
    public ResponseEntity<Iterable<Category>> findAll() {
        log.info("Fetching all categories...");
        Iterable<Category> categories = this.categoryService.findAll();
        log.info("Fetched {} categories", ((Collection<?>) categories).size());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Integer id) {
        log.info("Fetching category with id: {}", id);
        Category category = this.categoryService.findById(id);
        log.info("Category found: {}", category);
        return ResponseEntity.ok(category);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        log.warn("Deleting category with id: {}", id);
        this.categoryService.deleteById(id);
        log.warn("Category deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
