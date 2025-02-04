package com.pgs.ecommerce.Ecommerce.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pgs.ecommerce.Ecommerce.application.CategoryService;
import com.pgs.ecommerce.Ecommerce.application.OrderService;
import com.pgs.ecommerce.Ecommerce.application.ProductService;
import com.pgs.ecommerce.Ecommerce.application.RegistrationService;
import com.pgs.ecommerce.Ecommerce.application.UploadFileService;
import com.pgs.ecommerce.Ecommerce.application.UserService;
import com.pgs.ecommerce.Ecommerce.domain.port.ICategoryRepository;
import com.pgs.ecommerce.Ecommerce.domain.port.IOrderRepository;
import com.pgs.ecommerce.Ecommerce.domain.port.IProductRepository;
import com.pgs.ecommerce.Ecommerce.domain.port.IUserRepository;

@Configuration
public class BeanConfiguration {
	
	@Bean
    UploadFileService uploadFileService () {
        return new UploadFileService();
    }
	
    @Bean
    UserService userService (IUserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    CategoryService categoryService (ICategoryRepository categoryRepository) {
        return new CategoryService(categoryRepository);
    }

    @Bean
    ProductService productService (IProductRepository productRepository, UploadFileService fileService) {
        return new ProductService(productRepository, fileService);
    }
    
    @Bean
    OrderService orderService (IOrderRepository orderRepository) {
        return new OrderService(orderRepository);
    }
    
    @Bean
    RegistrationService registrationService(IUserRepository iUserRepository) {
    	return new RegistrationService(iUserRepository);
    }


}
