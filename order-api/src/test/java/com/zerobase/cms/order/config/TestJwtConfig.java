package com.zerobase.cms.order.config;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.crypto.SecretKey;

@TestConfiguration
@Profile("test")
public class TestJwtConfig {
    
    @Bean
    @Primary
    public SecretKey testJwtSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Bean
    @Primary
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(testJwtSecretKey());
    }
} 