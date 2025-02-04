package com.pgs.ecommerce.Ecommerce.infrastructure.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.ecommerce.Ecommerce.infrastructure.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/security")
@Slf4j
@AllArgsConstructor
public class LoginController {
	
	private final  AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<String> login (@RequestBody UserDTO dto) {
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
		);
		// Set the authenticated user in the application context
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return new ResponseEntity<>("User logged successfully", HttpStatus.OK);
				
	}

}
