package com.zerobase.domain.config;

import com.zerobase.domain.common.UserType;
import com.zerobase.domain.common.UserVo;
import com.zerobase.domain.util.Aes256Util;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider {

    private final Key secretKey;

    public JwtAuthenticationProvider(
        @Value("${jwt.secret:RMwRqIhqkjeIU+rZuozwZk1DYjYJSC+WgN5bLGp/mrQ=}") String secret
    ) {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String userPK, Long id, UserType userType) {
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPK));
        claims.put("roles", userType);

        Date now = new Date();
        long tokenValidTime = 1000L * 60 * 60 * 24; // 24 hours

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(Aes256Util.encrypt(userPK))
            .setId(Aes256Util.encrypt(id.toString()))
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidTime))
            .signWith(secretKey, SignatureAlgorithm.HS256) // Deprecated 메서드 수정
            .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey) // Deprecated 메서드 수정
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
            .setSigningKey(secretKey) // Deprecated 메서드 수정
            .build()
            .parseClaimsJws(token)
            .getBody();

        return new UserVo(
            Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(claims.getId()))),
            Aes256Util.decrypt(claims.getSubject())
        );
    }
}
