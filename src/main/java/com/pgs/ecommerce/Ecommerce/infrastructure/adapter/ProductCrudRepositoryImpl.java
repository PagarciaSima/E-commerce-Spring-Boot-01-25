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

@Repository
@AllArgsConstructor
public class ProductCrudRepositoryImpl implements IProductRepository {

    private final IProductCrudRepository iProductCrudRepository;
    private final IProductMapper IProductMapper;

    @Override
    public Product save(Product product) {
    	product.setCode(generateProductCode());
        return IProductMapper.toProduct(iProductCrudRepository.save(IProductMapper.toProductEntity(product)));
    }
    
    private String generateProductCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        
        // Generar un código único con fecha y un valor aleatorio
        String randomPart = UUID.randomUUID().toString().substring(0, 5); 
        return "PROD_" + date + "_" + randomPart;
    }
    
    @Override
    public Product update(Integer id, Product product) {
    	this.findById(id);

        product.setId(id);
        return IProductMapper.toProduct(
            iProductCrudRepository.save(IProductMapper.toProductEntity(product))
        );
    }

    @Override
    public Iterable<Product> findAll() {
        return IProductMapper.toProductList(iProductCrudRepository.findAll());
    }

    @Override
    public Product findById(Integer id) {
        return IProductMapper.toProduct(iProductCrudRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Product ID " + id + " not found")
        ));
    }

    @Override
    public void deleteById(Integer id) {
        Product product = this.findById(id);
        if (null != product)
         iProductCrudRepository.deleteById(id);
    }
}
