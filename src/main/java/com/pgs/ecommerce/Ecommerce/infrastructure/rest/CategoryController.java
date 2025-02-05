package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pgs.ecommerce.Ecommerce.application.CategoryService;
import com.pgs.ecommerce.Ecommerce.domain.model.Category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Categories", description = "API for managing product categories")
@SecurityRequirement(name = "bearerAuth")  
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Creates a new category.
     *
     * @param category the category to be created
     * @return a {@link ResponseEntity} containing the created category and HTTP status {@code 201 (Created)}
     */
    @Operation(
        summary = "Create a new category",
        description = "Creates a new product category in the system.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Category successfully created",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Category.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request")
        }
    )
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
    @Operation(
        summary = "Update an existing category",
        description = "Updates an existing category by its ID.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Category successfully updated",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Category.class)
                )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Category not found")
        }
    )
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
    @Operation(
        summary = "Get all categories",
        description = "Retrieves a list of all product categories.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of all categories",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Category.class))
                )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
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
    @Operation(
        summary = "Get category by ID",
        description = "Retrieves a category by its ID.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Category found",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Category.class)
                )
            ),
            @ApiResponse(responseCode = "404", description = "Category not found")
        }
    )
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
    @Operation(
        summary = "Delete category by ID",
        description = "Deletes a category by its ID.",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Category successfully deleted"
            ),
            @ApiResponse(responseCode = "404", description = "Category not found")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        log.warn("Deleting category with id: {}", id);
        this.categoryService.deleteById(id);
        log.warn("Category deleted successfully");
        return ResponseEntity.noContent().build();
    }
}
