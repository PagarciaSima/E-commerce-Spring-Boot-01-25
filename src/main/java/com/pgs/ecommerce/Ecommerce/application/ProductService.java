package com.pgs.ecommerce.Ecommerce.application;

import com.pgs.ecommerce.Ecommerce.domain.port.IProductRepository;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.web.multipart.MultipartFile;

import com.pgs.ecommerce.Ecommerce.domain.model.Category;
import com.pgs.ecommerce.Ecommerce.domain.model.Product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class ProductService {

    private final IProductRepository iProductRepository;
    private final UploadFileService uploadFileService;

    public Product save (Product product, MultipartFile multipartFile) throws IOException {
        String newImagePath = uploadFileService.upload(multipartFile);
        product.setUrlImage(newImagePath); 
	  	    	
        return this.iProductRepository.save(product);
    }

    public Iterable<Product> findAll() {
        return this.iProductRepository.findAll();
    }

    public Product findById (Integer id) {
    	Product product = iProductRepository.findById(id);
    	if (null == product)
            throw new NoSuchElementException("Product not found with id: " + id); 
        return  this.iProductRepository.findById(id);
    }

    public void deleteById (Integer id) {
    	Product product = this.findById(id);
    	String urlImage = product.getUrlImage();
    	String imageName = urlImage != null ? urlImage.substring(29) : "default.jpg";
    	log.debug("Image name: ", imageName);
    	if(!imageName.equals("default.jpg")) {
    		uploadFileService.delete(imageName);
    	}
        this.iProductRepository.deleteById(id);
    }

    public Product update(Integer id, Product product, MultipartFile multipartFile) throws IOException {
    	String urlImage = this.findById(id).getUrlImage();
    	String imageName = urlImage != null ? urlImage.substring(29) : "default.jpg";
    	log.debug("Image name: ", imageName);
    	if(!imageName.equals("default.jpg")) {
    		uploadFileService.delete(imageName);
    	}
    	String newImagePath = uploadFileService.upload(multipartFile);
        product.setUrlImage(newImagePath); 

        return this.iProductRepository.update(id, product);
    }
}
