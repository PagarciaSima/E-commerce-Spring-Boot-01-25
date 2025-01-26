package com.pgs.ecommerce.Ecommerce.application;

import com.pgs.ecommerce.Ecommerce.domain.port.IProductRepository;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.pgs.ecommerce.Ecommerce.domain.model.Product;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService {

    private final IProductRepository iProductRepository;
    private final UploadFileService uploadFileService;

    public Product save (Product product, MultipartFile multipartFile) throws IOException {
		if (null != multipartFile) {
			product.setUrlImage(uploadFileService.upload(multipartFile));
		}
    	
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

	public Product update(Integer id, Product product, MultipartFile multipartFile) throws IOException {
		if (null != multipartFile) {
			product.setUrlImage(uploadFileService.upload(multipartFile));
		}
		return this.iProductRepository.update(id, product);
	}
}
