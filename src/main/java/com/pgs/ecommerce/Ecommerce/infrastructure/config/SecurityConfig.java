package com.pgs.ecommerce.Ecommerce.infrastructure.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pgs.ecommerce.Ecommerce.infrastructure.jwt.JwtAuthorizationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig implements WebMvcConfigurer{
	
	private final JwtAuthorizationFilter jwtAuthorizationFilter;
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        		.cors(
    				cors -> cors.configurationSource(
    					request -> {
    						CorsConfiguration configuration = new CorsConfiguration();
    						configuration.setAllowedOrigins(Arrays.asList("*"));
    						configuration.setAllowedMethods(Arrays.asList("*"));
    						configuration.setAllowedHeaders(Arrays.asList("*"));
    						return configuration;
    					}
					)
				)
        		.csrf(csrf -> csrf.disable()).authorizeHttpRequests(
                auth -> 
                auth
                    .requestMatchers("/api/v1/admin/categories/**").hasRole("ADMIN")
                    .requestMatchers("/api/v1/admin/products**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers("/api/v1/orders/**").hasRole("USER")
                    .requestMatchers("/api/v1/payments/**").hasRole("USER")
                    .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**", "/actuator/**").permitAll()
                    .requestMatchers("/api/v1/security/**").permitAll()
                    .requestMatchers("/images/**").permitAll()
                    .anyRequest().authenticated()
                /* auth -> 
                auth.anyRequest().permitAll() */
        );

        // Añadir el filtro JWT antes del filtro de autenticación predeterminado
        httpSecurity.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        
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
