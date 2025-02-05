package com.pgs.ecommerce.Ecommerce.infrastructure.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.pgs.ecommerce.Ecommerce.domain.model.Product;
import com.pgs.ecommerce.Ecommerce.domain.port.IProductRepository;
import com.pgs.ecommerce.Ecommerce.infrastructure.mapper.IProductMapper;

import lombok.AllArgsConstructor;

/**
 * Implementation of the {@link IProductRepository} interface that provides
 * methods to interact with the product data source.
 */
@Repository
@AllArgsConstructor
public class ProductCrudRepositoryImpl implements IProductRepository {

    private final IProductCrudRepository iProductCrudRepository;
    private final IProductMapper iProductMapper;

    /**
     * Saves a new product to the repository. Generates a unique product code
     * before saving.
     *
     * @param product the product to be saved
     * @return the saved product with an assigned ID and code
     */
    @Override
    public Product save(Product product) {
        product.setCode(generateProductCode());
        return iProductMapper.toProduct(iProductCrudRepository.save(iProductMapper.toProductEntity(product)));
    }

    /**
     * Generates a unique product code based on the current date and a random
     * UUID.
     *
     * @return a unique product code in the format "PROD_yyyy-MM-dd_xxxxx"
     */
    private String generateProductCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        // Generate a unique code with date and a random value
        String randomPart = UUID.randomUUID().toString().substring(0, 5);
        return "PROD_" + date + "_" + randomPart;
    }

    /**
     * Updates an existing product in the repository. Ensures the product exists
     * before updating.
     *
     * @param id      the ID of the product to update
     * @param product the updated product data
     * @return the updated product
     * @throws NoSuchElementException if the product with the specified ID is not
     *                                found
     */
    @Override
    public Product update(Integer id, Product product) {
        this.findById(id);

        product.setId(id);
        return iProductMapper.toProduct(
                iProductCrudRepository.save(iProductMapper.toProductEntity(product)));
    }

    /**
     * Retrieves all products from the repository.
     *
     * @return an iterable collection of all products
     */
    @Override
    public Iterable<Product> findAll() {
        return iProductMapper.toProductList(iProductCrudRepository.findAll());
    }

    /**
     * Finds a product by its ID.
     *
     * @param id the ID of the product to find
     * @return the found product
     * @throws NoSuchElementException if the product with the specified ID is not
     *                                found
     */
    @Override
    public Product findById(Integer id) {
        return iProductMapper.toProduct(iProductCrudRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Product ID " + id + " not found")));
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to delete
     * @throws NoSuchElementException if the product with the specified ID is not
     *                                found
     */
    @Override
    public void deleteById(Integer id) {
        Product product = this.findById(id);
        if (product != null)
            iProductCrudRepository.deleteById(id);
    }

}
