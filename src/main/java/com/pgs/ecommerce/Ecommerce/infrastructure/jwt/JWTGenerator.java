package com.pgs.ecommerce.Ecommerce.infrastructure.jwt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTGenerator {

    public String getToken(String username) {
        List<String> roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        String token = Jwts.builder()
            .setId("E-commerce")
            .setSubject(username)
            .claim("authorities", roles) // Guardamos los roles como lista de strings
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + Constants.TOKEN_EXPIRATION_TIME))
            .signWith(Constants.getSignedKey(Constants.SECRET_KEY), SignatureAlgorithm.HS512)
            .compact();

        return "Bearer " + token;
    }
}
