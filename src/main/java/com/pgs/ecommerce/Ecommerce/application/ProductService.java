package com.pgs.ecommerce.Ecommerce.application;

import com.pgs.ecommerce.Ecommerce.domain.port.IProductRepository;
import com.pgs.ecommerce.Ecommerce.domain.model.Product;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService {

    private final IProductRepository iProductRepository;

    public Product save (Product product) {
        return this.iProductRepository.save(product);
    }

    public Iterable<Product> findAll() {
        return this.iProductRepository.findAll();
    }

    public Product findById (Integer id) {
        return  this.iProductRepository.findById(id);
    }

    public void deleteById (Integer id) {
        this.iProductRepository.deleteById(id);
    }

	public Product update(Integer id, Product product) {
		return this.iProductRepository.update(id, product);
	}
}
