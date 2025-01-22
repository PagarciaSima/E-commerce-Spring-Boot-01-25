package com.pgs.ecommerce.Ecommerce.infrastructure.adapter;

import com.pgs.ecommerce.Ecommerce.domain.model.Product;
import com.pgs.ecommerce.Ecommerce.domain.port.IProductRepository;
import com.pgs.ecommerce.Ecommerce.infrastructure.mapper.IProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Repository
@AllArgsConstructor
public class ProductCrudRepositoryImpl implements IProductRepository {

    private final IProductCrudRepository iProductCrudRepository;
    private final IProductMapper IProductMapper;

    @Override
    public Product save(Product product) {
        return IProductMapper.toProduct(iProductCrudRepository.save(IProductMapper.toProductEntity(product)));
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
