package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import com.pgs.ecommerce.Ecommerce.application.ProductService;
import com.pgs.ecommerce.Ecommerce.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing products in the e-commerce platform. 
 * Provides endpoints for saving, updating, retrieving, and deleting products, 
 * as well as uploading images for products.
 */
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/admin/products")
@Slf4j
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Creates a new product and optionally associates an image with it.
     * 
     * @param product The product to be created.
     * @param image The image to be associated with the product (optional).
     * @return ResponseEntity containing the saved product and the status code.
     */
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Product> save(
            @RequestPart("product") Product product,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            log.info("Saving product: {}", product.getName());
            if (image != null) {
                log.info("Saving image for product: {}", product.getName());
            }
            Product savedProduct = this.productService.save(product, image);
            log.info("Product saved successfully with id: {}", savedProduct.getId());
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error saving product: {}", product.getName(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates an existing product and optionally updates its image.
     * 
     * @param id The ID of the product to be updated.
     * @param product The updated product data.
     * @param image The new image to be associated with the product (optional).
     * @return ResponseEntity containing the updated product and the status code.
     */
    @PutMapping(value = "{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Product> update(
            @PathVariable Integer id,
            @RequestPart("product") Product product,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            log.info("Updating product with id: {}", id);
            if (image != null) {
                log.info("Updating image for product with id: {}", id);
            }
            Product updatedProduct = this.productService.update(id, product, image);
            log.info("Product updated successfully with id: {}", updatedProduct.getId());
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating product with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves all products in the system.
     * 
     * @return ResponseEntity containing the list of all products.
     */
    @GetMapping
    public ResponseEntity<Iterable<Product>> findAll() {
        log.info("Fetching all products...");
        Iterable<Product> products = this.productService.findAll();
        log.info("Fetched {} products", ((Collection<?>) products).size());
        return ResponseEntity.ok(products);
    }

    /**
     * Retrieves a product by its ID.
     * 
     * @param id The ID of the product to retrieve.
     * @return ResponseEntity containing the product and the status code.
     */
    @GetMapping("{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id) {
        log.info("Fetching product with id: {}", id);
        try {
            Product product = this.productService.findById(id);
            log.info("Product found: {}", product.getName());
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            log.error("Product with id {} not found", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id The ID of the product to delete.
     * @return ResponseEntity with no content if successful, or error status if failed.
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Product> deleteById(@PathVariable Integer id) {
        log.warn("Deleting product with id: {}", id);
        try {
            this.productService.deleteById(id);
            log.info("Product with id {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting product with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
