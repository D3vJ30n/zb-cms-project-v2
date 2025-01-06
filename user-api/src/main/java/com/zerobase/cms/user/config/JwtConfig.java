package com.zerobase.cms.user.config;

import com.zerobase.domain.config.JwtAuthenticationProvider;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtConfig {
    // 시크릿 키는 최소 32바이트(256비트) 이상
    private static final String SECRET_KEY = 
        "your_secret_key_must_be_at_least_32_bytes_long_for_hs256_1234567";

    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(jwtSecretKey());
    }
}