package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import com.pgs.ecommerce.Ecommerce.application.CategoryService;
import com.pgs.ecommerce.Ecommerce.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/categories")
@AllArgsConstructor
@Slf4j
// http://localhost:8085/api/v1/admin/categories
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Category save(@RequestBody Category category) {
        return this.categoryService.save(category);
    }

    @GetMapping
    public Iterable<Category> findAll () {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById (@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Category deleteById (@PathVariable Integer id) {
        return categoryService.findById(id);
    }
}
