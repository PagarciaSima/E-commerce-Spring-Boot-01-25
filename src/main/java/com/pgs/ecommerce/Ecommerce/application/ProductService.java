package com.pgs.ecommerce.Ecommerce.application;

import java.io.IOException;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.web.multipart.MultipartFile;

import com.pgs.ecommerce.Ecommerce.domain.model.Product;
import com.pgs.ecommerce.Ecommerce.domain.port.IProductRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for managing products in the system.
 */
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final IProductRepository iProductRepository;
    private final UploadFileService uploadFileService;

    /**
     * Saves a new product along with its image.
     *
     * @param product       The product to be saved.
     * @param multipartFile The image file associated with the product.
     * @return The saved product with its assigned ID and image URL.
     * @throws IOException If an error occurs while uploading the image.
     */
    public Product save(Product product, MultipartFile multipartFile) throws IOException {
        log.info("Saving new product: {}", product);
        String newImagePath = uploadFileService.upload(multipartFile);
        product.setUrlImage(newImagePath);
        Product savedProduct = this.iProductRepository.save(product);
        log.info("Product saved successfully with ID: {}", savedProduct.getId());
        return savedProduct;
    }

    /**
     * Retrieves all stored products.
     *
     * @return An iterable containing all products.
     */
    public Iterable<Product> findAll() {
        log.info("Fetching all products");
        Iterable<Product> products = this.iProductRepository.findAll();
        log.info("Retrieved {} products", ((Collection<?>) products).size());
        return products;
    }

    /**
     * Finds a product by its ID.
     *
     * @param id The ID of the product to find.
     * @return The found product.
     * @throws NoSuchElementException If the product does not exist.
     */
    public Product findById(Integer id) {
        log.info("Fetching product with ID: {}", id);
        Product product = iProductRepository.findById(id);
        if (product == null) {
            log.warn("Product with ID: {} not found", id);
            throw new NoSuchElementException("Product not found with id: " + id);
        }
        log.info("Product retrieved successfully: {}", product);
        return product;
    }

    /**
     * Deletes a product by its ID, including its associated image if applicable.
     *
     * @param id The ID of the product to delete.
     */
    public void deleteById(Integer id) {
        log.info("Deleting product with ID: {}", id);
        Product product = this.findById(id);
        String urlImage = product.getUrlImage();
        String imageName = urlImage != null ? urlImage.substring(29) : "default.jpg";
        log.debug("Image name to delete: {}", imageName);
        if (!imageName.equals("default.jpg")) {
            uploadFileService.delete(imageName);
            log.info("Deleted image: {}", imageName);
        }
        this.iProductRepository.deleteById(id);
        log.info("Product with ID: {} deleted successfully", id);
    }

    /**
     * Updates an existing product along with its image.
     *
     * @param id            The ID of the product to update.
     * @param product       The updated product data.
     * @param multipartFile The new image file for the product.
     * @return The updated product.
     * @throws IOException If an error occurs while uploading the new image.
     */
    public Product update(Integer id, Product product, MultipartFile multipartFile) throws IOException {
        log.info("Updating product with ID: {}", id);
        String urlImage = this.findById(id).getUrlImage();
        String imageName = urlImage != null ? urlImage.substring(29) : "default.jpg";
        log.debug("Previous image name: {}", imageName);
        if (!imageName.equals("default.jpg")) {
            uploadFileService.delete(imageName);
            log.info("Deleted old image: {}", imageName);
        }
        String newImagePath = uploadFileService.upload(multipartFile);
        product.setUrlImage(newImagePath);
        Product updatedProduct = this.iProductRepository.update(id, product);
        log.info("Product with ID: {} updated successfully", id);
        return updatedProduct;
    }
}
