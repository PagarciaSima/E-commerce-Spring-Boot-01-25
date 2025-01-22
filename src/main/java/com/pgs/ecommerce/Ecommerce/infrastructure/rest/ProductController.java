package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import com.pgs.ecommerce.Ecommerce.application.ProductService;
import com.pgs.ecommerce.Ecommerce.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/products")
@Slf4j
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> save (@RequestBody Product product) {
        return new ResponseEntity<>(this.productService.save(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        return ResponseEntity.ok(this.productService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(this.productService.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> deleteById(@PathVariable Integer id) {
        this.productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
