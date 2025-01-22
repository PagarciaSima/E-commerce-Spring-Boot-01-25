package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import com.pgs.ecommerce.Ecommerce.application.CategoryService;
import com.pgs.ecommerce.Ecommerce.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/categories")
@AllArgsConstructor
@Slf4j
// http://localhost:8085/api/v1/admin/categories
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {
        return new ResponseEntity<>(this.categoryService.save(category), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Category>> findAll () {
        return ResponseEntity.ok(this.categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findById (@PathVariable Integer id) {
        return ResponseEntity.ok(this.categoryService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteById (@PathVariable Integer id) {
        this.categoryService.findById(id);
        return ResponseEntity.ok().build();
    }
}
