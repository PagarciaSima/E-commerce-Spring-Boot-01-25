package com.pgs.ecommerce.Ecommerce.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
				auth -> 
				auth.requestMatchers("/api/v1/admin/categories/**").hasRole("ADMIN")
				.requestMatchers("/api/v1/products/**").hasAnyRole("ADMIN", "USER") 
				.requestMatchers("/api/v1/orders/**").hasRole("USER")
				.requestMatchers("/api/v1/payments/**").hasRole("USER")
				.requestMatchers("/api/v1/security/**").permitAll()
				.anyRequest().authenticated()
		);
		return httpSecurity.build();
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}
	
	// Delegates authentication from filter chain to Custom User Detail Service
	@Bean
	AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
