package com.pgs.ecommerce.Ecommerce.domain.port;

import com.pgs.ecommerce.Ecommerce.domain.model.Product;

public interface IProductRepository {
    Product save (Product product);
    Iterable<Product> findAll();
    Product findById (Integer id);
    void deleteById (Integer id);
	Product update(Integer id, Product product);
}
