package com.zerobase.domain.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Component
public class JwtAuthenticationProvider {
    private final long tokenValidTime = 1000L * 60 * 60 * 24;
    private final SecretKey key;

    public JwtAuthenticationProvider(SecretKey key) {
        this.key = key;
    }

    public String createToken(String userPk, Long id, UserType userType) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("id", id);
        claims.put("roles", userType);
        Date now = new Date();
        
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public UserVo getUserVo(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
                
        return new UserVo(
                claims.getSubject(),
                Long.valueOf(Objects.requireNonNull(claims.get("id")).toString()),
                UserType.valueOf(claims.get("roles").toString())
        );
    }
} 