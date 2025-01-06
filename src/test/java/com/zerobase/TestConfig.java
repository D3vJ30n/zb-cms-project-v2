package com.zerobase;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.crypto.SecretKey;

@TestConfiguration
public class TestConfig {
    
    @Bean
    @Primary
    public SecretKey jwtSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    @Primary
    public JwtAuthenticationProvider jwtAuthenticationProvider(SecretKey secretKey) {
        return new JwtAuthenticationProvider(secretKey);
    }
} 