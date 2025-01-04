package com.zerobase.domain.api.config;

import com.zerobase.domain.api.common.UserType;
import com.zerobase.domain.api.common.UserVo;
import com.zerobase.domain.api.util.Aes256Util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider {
    private final Key secretKey;

    public JwtAuthenticationProvider() {
        String secret = "your-secret-key-here-must-be-at-least-32-bytes-long-for-hs256";
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String userPK, Long id, UserType userType) {
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPK));
        claims.put("roles", userType);

        Date now = new Date();
        long tokenValidTime = 1000L * 60 * 60 * 24;

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(Aes256Util.encrypt(userPK))
            .setId(Aes256Util.encrypt(id.toString()))
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidTime))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getExpiration()
                .before(new Date());
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public UserVo getUserVo(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();

        return new UserVo(
            Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(claims.getId()))),
            Aes256Util.decrypt(claims.getSubject())
        );
    }
}
