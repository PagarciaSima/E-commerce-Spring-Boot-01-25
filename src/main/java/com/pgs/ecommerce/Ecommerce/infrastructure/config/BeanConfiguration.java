package com.pgs.ecommerce.Ecommerce.infrastructure.config;

import com.pgs.ecommerce.Ecommerce.application.CategoryService;
import com.pgs.ecommerce.Ecommerce.application.OrderService;
import com.pgs.ecommerce.Ecommerce.application.ProductService;
import com.pgs.ecommerce.Ecommerce.application.UserService;
import com.pgs.ecommerce.Ecommerce.domain.port.ICategoryRepository;
import com.pgs.ecommerce.Ecommerce.domain.port.IOrderRepository;
import com.pgs.ecommerce.Ecommerce.domain.port.IProductRepository;
import com.pgs.ecommerce.Ecommerce.domain.port.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public UserService userService (IUserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public CategoryService categoryService (ICategoryRepository categoryRepository) {
        return new CategoryService(categoryRepository);
    }

    @Bean
    public ProductService productService (IProductRepository productRepository) {
        return new ProductService(productRepository);
    }
    
    @Bean
    public OrderService orderService (IOrderRepository orderRepository) {
        return new OrderService(orderRepository);
    }


}
