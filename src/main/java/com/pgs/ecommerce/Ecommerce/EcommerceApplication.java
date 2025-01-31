package com.pgs.ecommerce.Ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		 Dotenv dotenv = Dotenv.configure().load();

	        // Acceder a las variables de entorno
	        String paypalClientId = dotenv.get("PAYPAL_CLIENT_ID");
	        String paypalSecret = dotenv.get("PAYPAL_SECRET");
	        System.out.println("PayPal Client ID: " + paypalClientId);

		SpringApplication.run(EcommerceApplication.class, args);
	}

}
