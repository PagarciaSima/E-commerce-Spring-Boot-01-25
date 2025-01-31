package com.pgs.ecommerce.Ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		Dotenv.configure().load();

		SpringApplication.run(EcommerceApplication.class, args);
	}

}
